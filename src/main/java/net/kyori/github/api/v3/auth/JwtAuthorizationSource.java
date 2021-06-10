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
package net.kyori.github.api.v3.auth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import net.kyori.github.api.v3.auth.implementation.GitHubAppJwtAuthorizationSource;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A JWT-based authorization source.
 *
 * @since 2.0.0
 */
public interface JwtAuthorizationSource extends AuthorizationSource {
  /**
   * Creates a new JWT-based authorization source for a GitHub App.
   *
   * @param appId the id of the GitHub App
   * @param privateKeyText the private key of the GitHub App, in PEM form
   * @return the new authorization source
   * @since 2.0.0
   */
  static @NonNull JwtAuthorizationSource forGitHubApp(final @NonNull String appId, final @NonNull String privateKeyText) {
    return new GitHubAppJwtAuthorizationSource(appId, privateKeyText);
  }

  /**
   * Creates a new JWT-based authorization source for a GitHub App.
   *
   * <p>The {@code privateKeyFile} must contain UTF-8 encoded text.</p>
   *
   * @param appId  the id of the GitHub App
   * @param privateKeyFile the private key of the GitHub App, in PEM form
   * @return the new authorization source
   * @throws IOException if the file could not be read
   * @since 2.0.0
   */
  static @NonNull JwtAuthorizationSource forGitHubApp(final @NonNull String appId, final @NonNull Path privateKeyFile) throws IOException {
    return forGitHubApp(appId, new String(Files.readAllBytes(privateKeyFile), StandardCharsets.UTF_8)); // todo: Files.readString
  }

  @Override
  default @NonNull String get() {
    return "Bearer " + this.currentJwt();
  }

  /**
   * Gets the current JWT.
   *
   * @return the JWT
   * @since 2.0.0
   */
  @NonNull String currentJwt();
}
