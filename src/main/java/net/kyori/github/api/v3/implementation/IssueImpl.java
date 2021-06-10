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

import java.util.Optional;
import net.kyori.github.api.v3.PullRequest;
import net.kyori.github.api.v3.Repository;
import net.kyori.github.api.v3.User;
import org.checkerframework.checker.nullness.qual.NonNull;

final class IssueImpl extends AbstractIssue {
  private final Repository repository;
  private final Lazy<Partial.Issue> lazy;

  IssueImpl(final Repository repository, final HTTP.RequestTemplate request, final int number) {
    super(request, number);
    this.repository = repository;
    this.lazy = new Lazy<>(this.request, Partial.Issue.class);
  }

  @Override
  public @NonNull String html_url() {
    return this.lazy.get().html_url;
  }

  @Override
  public @NonNull User user() {
    return new UserImpl(this.lazy.get().user.login, this.lazy.get().user.name, this.lazy.get().user.avatar_url);
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
  public @NonNull Optional<PullRequest> pullRequest() {
    if (this.lazy.get().pull_request == null) {
      return Optional.empty();
    }
    return Optional.of(this.repository.pullRequests().get(this.number()));
  }
}
