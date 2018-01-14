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

import com.google.common.base.MoreObjects;
import net.kyori.blizzard.NonNull;

import java.util.Objects;

/**
 * An id for accessing a repository.
 */
public interface RepositoryId {
  /**
   * Creates a new repository id.
   *
   * @param user the user name
   * @param repo the repository name
   * @return the repository id
   */
  @NonNull
  static RepositoryId of(@NonNull final String user, @NonNull final String repo) {
    return new Impl(user, repo);
  }

  /**
   * Gets the user name.
   *
   * @return the user name
   */
  @NonNull
  String user();

  /**
   * Gets the repository name.
   *
   * @return the repository name
   */
  @NonNull
  String repo();

  /**
   * Gets the repository id as a string.
   *
   * @return string
   */
  @NonNull
  default String asString() {
    return this.user() + '/' + this.repo();
  }

  final class Impl implements RepositoryId {
    private final String user;
    private final String repo;

    Impl(final String user, final String repo) {
      this.user = user;
      this.repo = repo;
    }

    @NonNull
    @Override
    public String user() {
      return this.user;
    }

    @NonNull
    @Override
    public String repo() {
      return this.repo;
    }

    @Override
    public boolean equals(final Object other) {
      if(this == other) {
        return true;
      }
      if(other == null || !(other instanceof RepositoryId)) {
        return false;
      }
      final RepositoryId that = (RepositoryId) other;
      return Objects.equals(this.user, that.user())
        && Objects.equals(this.repo, that.repo());
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.user, this.repo);
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
        .add("user", this.user)
        .add("repo", this.repo)
        .toString();
    }
  }
}
