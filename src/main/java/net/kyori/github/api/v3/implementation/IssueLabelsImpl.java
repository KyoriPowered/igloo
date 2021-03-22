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
import java.util.Arrays;
import net.kyori.github.api.v3.IssueLabels;
import net.kyori.github.api.v3.Label;
import net.kyori.mu.function.ThrowingFunction;
import org.checkerframework.checker.nullness.qual.NonNull;

final class IssueLabelsImpl implements IssueLabels {
  private final HTTP.RequestTemplate request;

  IssueLabelsImpl(final HTTP.RequestTemplate request) {
    this.request = request.path("labels");
  }

  @Override
  @SuppressWarnings("RedundantThrows")
  public @NonNull Iterable<Label> all() throws IOException {
    return new Paginated<>(
      this.request,
      ThrowingFunction.of(HTTP.RequestTemplate::get),
      ThrowingFunction.of(response -> Arrays.stream(response.as(Partial.Label[].class)).map(label -> new LabelImpl(this.request.up(3), label.url, label.name, label.description, label.color)))
    );
  }

  @Override
  public void add(final @NonNull Iterable<String> names) throws IOException {
    this.request.post(names).close();
  }

  @Override
  public void set(final @NonNull Iterable<String> names) throws IOException {
    this.request.put(names).close();
  }

  @Override
  public void remove(final @NonNull String name) throws IOException {
    this.request.delete().close();
  }
}
