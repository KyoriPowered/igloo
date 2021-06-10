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
package net.kyori.github.api;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;

final class RepositoryIdentifierImpl implements RepositoryIdentifier {
  private final String user;
  private final String repo;

  RepositoryIdentifierImpl(final String user, final String repo) {
    this.user = user;
    this.repo = repo;
  }

  @Override
  public @NonNull String user() {
    return this.user;
  }

  @Override
  public @NonNull String repo() {
    return this.repo;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) return true;
    if (!(other instanceof RepositoryIdentifier)) return false;
    final RepositoryIdentifier that = (RepositoryIdentifier) other;
    return this.user.equals(that.user()) && this.repo.equals(that.repo());
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
