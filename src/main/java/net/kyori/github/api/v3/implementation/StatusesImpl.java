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

import java.io.IOException;
import net.kyori.github.api.v3.Status;
import net.kyori.github.api.v3.Statuses;
import org.checkerframework.checker.nullness.qual.NonNull;

final class StatusesImpl implements Statuses {
  private final HTTP.RequestTemplate request;

  StatusesImpl(final HTTP.RequestTemplate request) {
    this.request = request.path("statuses");
  }

  @Override
  public <C extends Status.AbstractCreate> @NonNull Status create(final @NonNull String sha, final @NonNull C create) throws IOException {
    final Partial.Status status = this.request.path(sha).post(create).as(Partial.Status.class);
    return new CreatedStatus(status.state, status.target_url, status.description, status.context);
  }
}
