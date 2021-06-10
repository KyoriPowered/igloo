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
package net.kyori.github.api.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.kyori.github.api.RepositoryIdentifier;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A repository.
 *
 * @since 2.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository implements RepositoryIdentifier {
  public String name;
  public User owner;

  @Override
  public @NonNull String user() {
    return this.owner.login;
  }

  @Override
  public @NonNull String repo() {
    return this.name;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) return true;
    if (other == null || this.getClass() != other.getClass()) return false;
    final Repository that = (Repository) other;
    if (!this.name.equals(that.name)) return false;
    return this.owner.equals(that.owner);
  }

  @Override
  public int hashCode() {
    int result = this.name.hashCode();
    result = 31 * result + this.owner.hashCode();
    return result;
  }
}
