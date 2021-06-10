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

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;
import net.kyori.github.api.v3.Installation;
import net.kyori.github.api.v3.auth.implementation.GitHubAppInstallationTokenAuthorizationSource;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A token-based authorization source.
 *
 * @since 2.0.0
 */
public interface TokenAuthorizationSource extends AuthorizationSource {
  /**
   * Create a token-based authorization source from a token supplier.
   *
   * @param token the token supplier
   * @return the new authentication source
   * @since 2.0.0
   */
  static @NonNull TokenAuthorizationSource of(final @NonNull String token) {
    return () -> token;
  }

  /**
   * Create a token-based authorization source from a token supplier.
   *
   * @param tokenSupplier the token supplier
   * @return the new authentication source
   * @since 2.0.0
   */
  static @NonNull TokenAuthorizationSource from(final @NonNull Supplier<@NonNull String> tokenSupplier) {
    return tokenSupplier::get;
  }

  /**
   * Create a token-based authorization source from an installation.
   *
   * <p>
   * The installation must be part of a GitHub-App-authorized instance.
   * </p>
   *
   * <p>
   * This is equivalent to calling {@link #forInstallation(Installation, ScheduledExecutorService)} with
   * a brand new single-threaded {@link ScheduledExecutorService}.
   * </p>
   *
   * @param installation the installation to get access tokens from
   * @return the new authentication source
   * @since 2.0.0
   */
  static @NonNull TokenAuthorizationSource forInstallation(final @NonNull Installation installation) {
    final ScheduledExecutorService refreshExecutor = Executors.newSingleThreadScheduledExecutor(
      new ThreadFactoryBuilder().setDaemon(true).setNameFormat("igloo-installation-token-refresh-%d").build()
    );
    return forInstallation(installation, refreshExecutor);
  }

  /**
   * Create a token-based authorization source from an installation.
   *
   * <p>
   * The installation must be part of a GitHub-App-authorized instance.
   * </p>
   *
   * @param installation the installation to get access tokens from
   * @param refreshExecutor the executor to schedule refresh tasks on
   * @return the new authentication source
   * @since 2.0.0
   */
  static @NonNull TokenAuthorizationSource forInstallation(final @NonNull Installation installation,
                                                           final @NonNull ScheduledExecutorService refreshExecutor) {
    return new GitHubAppInstallationTokenAuthorizationSource(
      installation::createAccessToken, refreshExecutor
    );
  }

  @Override
  default @NonNull String get() {
    return "token " + this.currentToken();
  }

  /**
   * Gets the current authorization token.
   *
   * @return the authorization token
   * @since 2.0.0
   */
  @NonNull String currentToken();
}
