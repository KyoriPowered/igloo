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
 * An id for accessing a repository.
 *
 * @since 2.0.0
 */
public interface RepositoryId {
  /**
   * Creates a new repository id.
   *
   * @param user the user name
   * @param repo the repository name
   * @return the repository id
   * @since 2.0.0
   */
  static @NonNull RepositoryId of(final @NonNull String user, final @NonNull String repo) {
    return new RepositoryIdImpl(user, repo);
  }

  /**
   * Gets the user name.
   *
   * @return the user name
   * @since 2.0.0
   */
  @NonNull String user();

  /**
   * Gets the repository name.
   *
   * @return the repository name
   * @since 2.0.0
   */
  @NonNull String repo();

  /**
   * Gets the repository id as a string.
   *
   * @return string
   * @since 2.0.0
   */
  default @NonNull String asString() {
    return this.user() + '/' + this.repo();
  }
}
