/*
 * This file is part of igloo, licensed under the MIT License.
 *
 * Copyright (c) 2018 KyoriPowered
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
package net.kyori.igloo.v3;

import net.kyori.igloo.http.Request;
import org.checkerframework.checker.nullness.qual.NonNull;

/* package */ final class PullRequestImpl implements PullRequest {
  final Request request;
  private final int number;
  private final Lazy<Partial.PullRequest> lazy;

  /* package */ PullRequestImpl(final Request request, final int number) {
    this.request = request.path(Integer.toString(number));
    this.number = number;
    this.lazy = new Lazy<>(this.request, Partial.PullRequest.class);
  }

  @Override
  public int number() {
    return this.number;
  }

  @Override
  public @NonNull String html_url() {
    return this.lazy.get().html_url;
  }

  @Override
  public @NonNull String title() {
    return this.lazy.get().title;
  }

  @Override
  public @NonNull String body() {
    return this.lazy.get().body;
  }

  @Override
  public @NonNull State state() {
    return this.lazy.get().state;
  }

  @Override
  public boolean merged() {
    return this.lazy.get().merged;
  }
}
