/*
 * This file is part of igloo, licensed under the MIT License.
 *
 * Copyright (c) 2018-2021 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.github.api.v3.auth.implementation;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.kyori.github.api.v3.auth.JwtAuthorizationSource;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A JWT-based authorization source for a GitHub App.
 *
 * @since 2.0.0
 */
public final class GitHubAppJwtAuthorizationSource implements JwtAuthorizationSource {

  private static PrivateKey readPemPrivateKey(final String privateKeyText) {
    final byte[] privateKeyBytes = Base64.getDecoder().decode(
      privateKeyText
        .replaceFirst("-----BEGIN.*?\n", "")
        .replaceFirst("-----END.*?\n", "")
        .replaceAll("\r?\n", "")
    );
    final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
    final KeyFactory kf;
    try {
      kf = KeyFactory.getInstance("RSA");
    } catch(final NoSuchAlgorithmException e) {
      throw new AssertionError("Missing RSA key factory", e);
    }
    final PrivateKey privateKey;
    try {
      privateKey = kf.generatePrivate(spec);
    } catch(final InvalidKeySpecException e) {
      throw new IllegalArgumentException("Invalid private key given", e);
    }
    return privateKey;
  }

  private final String appId;
  private final Key privateKey;
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  // The last JWT we generated, cached for performance
  private String lastJwt;
  // When should we recreate lastJwt so it never expires?
  private Instant refreshTime = Instant.MIN;

  /**
   * Creates a new JWT-based authorization source for a GitHub App.
   *
   * @param appId the ID of the GitHub App
   * @param privateKeyText the private key of the GitHub App, in PEM format
   * @since 2.0.0
   */
  public GitHubAppJwtAuthorizationSource(final String appId, final String privateKeyText) {
    this.appId = appId;
    this.privateKey = readPemPrivateKey(privateKeyText);
  }

  @Override
  public @NonNull String currentJwt() {
    this.lock.readLock().lock();
    try {
      if(this.refreshTime.isBefore(Instant.now())) {
        this.lock.readLock().unlock();
        try {
          this.refreshJwt();
        } finally {
          this.lock.readLock().lock();
        }
      }
      final String lastJwt = this.lastJwt;
      if(lastJwt == null) {
        throw new AssertionError("Failed to refresh JWT");
      }
      return lastJwt;
    } finally {
      this.lock.readLock().unlock();
    }
  }

  private void refreshJwt() {
    this.lock.writeLock().lock();
    try {
      // We might get the lock after it's been updated, if so just bail
      if(!this.refreshTime.isBefore(Instant.now())) {
        return;
      }
      final Instant now = Instant.now();
      this.lastJwt = Jwts.builder()
        .setIssuer(this.appId)
        // Allow up to 60 seconds of clock drift
        .setIssuedAt(Date.from(now.minus(1, ChronoUnit.MINUTES)))
        .setExpiration(Date.from(now.plus(10, ChronoUnit.MINUTES)))
        .signWith(this.privateKey, SignatureAlgorithm.RS256)
        .compact();
      // Refresh 1 minute before it would expire, to prevent clock drift errors
      this.refreshTime = now.plus(9, ChronoUnit.MINUTES);
    } finally {
      this.lock.writeLock().unlock();
    }
  }
}
