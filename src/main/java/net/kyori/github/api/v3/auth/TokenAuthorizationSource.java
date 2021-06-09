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

import java.util.function.Supplier;
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
  static TokenAuthorizationSource of(final @NonNull String token) {
    return () -> token;
  }

  /**
   * Create a token-based authorization source from a token supplier.
   *
   * @param tokenSupplier the token supplier
   * @return the new authentication source
   * @since 2.0.0
   */
  static TokenAuthorizationSource from(final @NonNull Supplier<@NonNull String> tokenSupplier) {
    return tokenSupplier::get;
  }

  @Override
  default @NonNull String currentAuthorization() {
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
