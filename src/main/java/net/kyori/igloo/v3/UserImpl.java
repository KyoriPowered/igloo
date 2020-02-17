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
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

/* package */ final class UserImpl implements User {
  private final String login;
  private final String name;
  private final String avatarUrl;

  /* package */ UserImpl(final String login, final String name, final String avatarUrl) {
    this.login = login;
    this.name = name;
    this.avatarUrl = avatarUrl;
  }

  @Override
  public @NonNull String login() {
    return this.login;
  }

  @Override
  public @NonNull String name() {
    return this.name;
  }

  @Override
  public @NonNull String avatarUrl() {
    return this.avatarUrl;
  }

  @Override
  public boolean equals(final Object other) {
    if(this == other) return true;
    if(!(other instanceof User)) return false;
    final User that = (User) other;
    return this.login.equals(that.login());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.login);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("login", this.login)
      .toString();
  }
}
