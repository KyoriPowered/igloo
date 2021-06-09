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
package net.kyori.github.api.v3.implementation;

import java.time.Instant;
import java.util.Collections;
import net.kyori.github.api.v3.Installation;
import org.checkerframework.checker.nullness.qual.NonNull;

final class InstallationImpl implements Installation {
  private final HTTP.RequestTemplate request;

  InstallationImpl(final HTTP.RequestTemplate request, final int id) {
    this.request = request.path(String.valueOf(id));
  }

  @Override
  public @NonNull AccessToken createAccessToken() {
    return new AccessTokenImpl(this.request);
  }

  private static final class AccessTokenImpl implements AccessToken {
    private final Lazy<Partial.AccessToken> lazy;

    private AccessTokenImpl(final HTTP.RequestTemplate request) {
      this.lazy = new Lazy<>(() -> request.path("access_tokens").post(Collections.emptyMap()), Partial.AccessToken.class);
    }

    @Override
    public @NonNull String token() {
      return this.lazy.get().token;
    }

    @Override
    public @NonNull Instant expiresAt() {
      return this.lazy.get().expires_at;
    }
  }
}
