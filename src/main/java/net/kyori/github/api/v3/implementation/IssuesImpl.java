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
import net.kyori.github.api.v3.Issue;
import net.kyori.github.api.v3.Issues;
import net.kyori.github.api.v3.Repository;
import org.checkerframework.checker.nullness.qual.NonNull;

final class IssuesImpl implements Issues {
  private final Repository repository;
  private final HTTP.RequestTemplate request;

  IssuesImpl(final Repository repository, final HTTP.RequestTemplate request) {
    this.repository = repository;
    this.request = request.path("issues");
  }

  @Override
  public @NonNull Issue get(final int number) {
    return new IssueImpl(this.repository, this.request, number);
  }

  @Override
  public <C extends Issue.AbstractCreate> @NonNull Issue create(final @NonNull C create) throws IOException {
    final Partial.Issue issue = this.request.post(create).as(Partial.Issue.class);
    return new CreatedIssue(this.request, issue);
  }
}
