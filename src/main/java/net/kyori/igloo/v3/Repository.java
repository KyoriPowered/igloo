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

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A repository.
 */
public interface Repository {
  /**
   * Gets collaborators.
   *
   * @return collaborators
   */
  @NonNull Collaborators collaborators();

  /**
   * Gets issues.
   *
   * @return issues
   */
  @NonNull Issues issues();

  /**
   * Gets labels.
   *
   * @return labels
   */
  Labels.@NonNull Repository labels();

  final class Impl implements Repository {
    private final Request request;

    Impl(final Request request, final RepositoryId id) {
      this.request = request.path("repos", id.user(), id.repo());
    }

    @Override
    public @NonNull Collaborators collaborators() {
      return new Collaborators.Impl(this.request);
    }

    @Override
    public @NonNull Issues issues() {
      return new Issues.Impl(this.request);
    }

    @Override
    public Labels.@NonNull Repository labels() {
      return new Labels.Repository.Impl(this.request);
    }
  }
}
