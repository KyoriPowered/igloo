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
package net.kyori.github.api.v3;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A repository.
 *
 * @since 2.0.0
 */
public interface Repository {
  /**
   * Gets collaborators.
   *
   * @return collaborators
   * @since 2.0.0
   */
  @NonNull Collaborators collaborators();

  /**
   * Gets issues.
   *
   * @return issues
   * @since 2.0.0
   */
  @NonNull Issues issues();

  /**
   * Gets labels.
   *
   * @return labels
   * @since 2.0.0
   */
  @NonNull RepositoryLabels labels();

  /**
   * Gets pull requests.
   *
   * @return pull requests
   * @since 2.0.0
   */
  @NonNull PullRequests pullRequests();

  /**
   * Gets statuses.
   *
   * @return statuses
   * @since 2.0.0
   */
  @NonNull Statuses statuses();
}
