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
import net.kyori.lunar.EvenMoreObjects;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.util.Objects;

final class LabelImpl implements Label {
  private final Request request;
  private final String url;
  private final String name;
  private final String color;

  LabelImpl(final Request request, final String url, final String name, final String color) {
    this.request = request.path(name);
    this.url = url;
    this.name = name;
    this.color = color;
  }

  @Override
  public @NonNull String url() {
    return this.url;
  }

  @Override
  public @NonNull String name() {
    return this.name;
  }

  @Override
  public @NonNull String color() {
    return this.color;
  }

  @Override
  public <E extends Edit> void edit(final @NonNull E edit) throws IOException {
    this.request.patch(edit).close();
  }

  @Override
  public void delete() throws IOException {
    this.request.delete().close();
  }

  @Override
  public boolean equals(final Object other) {
    return EvenMoreObjects.equals(Label.class, this, other, that -> {
      return Objects.equals(this.url, that.url())
        && Objects.equals(this.name, that.name())
        && Objects.equals(this.color, that.color());
    });
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.url, this.name, this.color);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("url", this.url)
      .add("name", this.name)
      .add("color", this.color)
      .toString();
  }
}
