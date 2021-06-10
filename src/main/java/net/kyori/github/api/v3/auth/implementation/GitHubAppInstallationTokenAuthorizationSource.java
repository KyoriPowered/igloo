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

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.kyori.github.api.v3.Installation;
import net.kyori.github.api.v3.auth.TokenAuthorizationSource;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A token-based authorization source for a GitHub App Installation.
 *
 * @since 2.0.0
 */
public final class GitHubAppInstallationTokenAuthorizationSource implements TokenAuthorizationSource {

  private final Callable<Installation.AccessToken> accessTokenProvider;
  private final ScheduledExecutorService refreshExecutor;
  private final CountDownLatch firstTokenAcquired = new CountDownLatch(1);
  private volatile CompletableFuture<String> currentToken;

  /**
   * Creates a new token-based authorization source for a GitHub App Installation.
   *
   * @param accessTokenProvider the access token provider
   * @param refreshExecutor the executor to refresh on
   * @since 2.0.0
   */
  public GitHubAppInstallationTokenAuthorizationSource(final Callable<Installation.AccessToken> accessTokenProvider,
                                                       final ScheduledExecutorService refreshExecutor) {
    this.accessTokenProvider = accessTokenProvider;
    this.refreshExecutor = refreshExecutor;
    this.refreshAt(Instant.now());
  }

  private void refreshAt(final Instant refreshTime) {
    final Duration refreshDuration = Duration.between(Instant.now(), refreshTime);
    this.refreshExecutor.schedule(this::refreshToken, refreshDuration.toMillis(), TimeUnit.MILLISECONDS);
  }

  private Void refreshToken() throws Exception {
    final Installation.AccessToken token;
    try {
      token = this.accessTokenProvider.call();
      this.currentToken = CompletableFuture.completedFuture(token.token());
    } catch(final Throwable t) {
      this.currentToken = new CompletableFuture<>();
      this.currentToken.completeExceptionally(t);
      throw t;
    } finally {
      this.firstTokenAcquired.countDown();
    }

    // Refresh early to ensure seamless operation
    this.refreshAt(token.expiresAt().minus(1, ChronoUnit.MINUTES));

    return null;
  }

  @Override
  public @NonNull String currentToken() {
    try {
      // Be generous, but do eventually time out
      if(!this.firstTokenAcquired.await(5, TimeUnit.MINUTES)) {
        throw new RuntimeException("Timed out while waiting for first token");
      }
    } catch(final InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Interrupted while waiting for first token", e);
    }
    final String currentToken = this.currentToken.join();
    if(currentToken == null) {
      throw new AssertionError("Missing current token");
    }
    return currentToken;
  }

}
