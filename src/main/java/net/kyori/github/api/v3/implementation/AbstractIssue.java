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

import com.google.api.client.http.EmptyContent;
import java.io.IOException;
import net.kyori.github.api.v3.Comments;
import net.kyori.github.api.v3.Issue;
import net.kyori.github.api.v3.IssueLabels;
import org.checkerframework.checker.nullness.qual.NonNull;

abstract class AbstractIssue implements Issue {
  final HTTP.RequestTemplate request;
  private final int number;

  AbstractIssue(final HTTP.RequestTemplate request, final int number) {
    this.request = request.path(Integer.toString(number));
    this.number = number;
  }

  @Override
  public int number() {
    return this.number;
  }

  @Override
  public <E extends Edit> void edit(final @NonNull E edit) throws IOException {
    this.request.patch(edit).close();
  }

  @Override
  public @NonNull IssueLabels labels() {
    return new IssueLabelsImpl(this.request);
  }

  @Override
  public @NonNull Comments comments() {
    return new CommentsImpl(this.request);
  }

  @Override
  public void lock() throws IOException {
    this.request.path("lock").put(new EmptyContent()).close();
  }

  @Override
  public void unlock() throws IOException {
    this.request.path("lock").delete().close();
  }
}
