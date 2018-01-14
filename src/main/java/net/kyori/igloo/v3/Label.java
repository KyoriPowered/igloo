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
import net.kyori.cereal.Document;

import java.io.IOException;
import java.util.Objects;

/**
 * A label that may be applied to an {@link Issue}.
 */
public interface Label {
  /**
   * Gets the url.
   *
   * @return the url
   */
  @NonNull
  String url();

  /**
   * Gets the name.
   *
   * @return the name
   */
  @NonNull
  String name();

  /**
   * Gets the color.
   *
   * @return the color
   */
  @NonNull
  String color();

  /**
   * Submits an edit to the label.
   *
   * @param edit the edit
   * @param <E> the edit type
   * @throws IOException if an exception occurs during edit
   */
  <E extends Edit> void edit(@NonNull final E edit) throws IOException;

  /**
   * Deletes the label.
   *
   * @throws IOException if an exception occurs during edit
   */
  void delete() throws IOException;

  /**
   * A document that can be submitted during label creation.
   */
  interface Create extends Document, Partial {
    /**
     * A document containing all information that may be submitted during creation.
     */
    interface Full extends Create, Partial.Name, Partial.Color {
    }
  }

  /**
   * A document that can be submitted during a label edit.
   */
  interface Edit extends Document, Partial {
    /**
     * A document containing all information that may be submitted during an edit.
     */
    interface Full extends Edit, Partial.Name, Partial.Color {
    }
  }

  /**
   * Partial documents used during label creation and edits.
   */
  interface Partial {
    /**
     * A document representing a label's name.
     */
    interface Name extends Edit {
      /**
       * Gets the label's name.
       *
       * @return the label name
       */
      @NonNull
      String name();
    }

    /**
     * A document representing a label's color.
     */
    interface Color extends Edit {
      /**
       * Gets the issue's color.
       *
       * @return the label color
       */
      @NonNull
      String color();
    }
  }

  final class Impl implements Label {
    private final Request request;
    private final String url;
    private final String name;
    private final String color;

    Impl(final Request request, final String url, final String name, final String color) {
      this.request = request.path(name);
      this.url = url;
      this.name = name;
      this.color = color;
    }

    @NonNull
    @Override
    public String url() {
      return this.url;
    }

    @NonNull
    @Override
    public String name() {
      return this.name;
    }

    @NonNull
    @Override
    public String color() {
      return this.color;
    }

    @Override
    public <E extends Edit> void edit(@NonNull final E edit) throws IOException {
      this.request.patch(edit);
    }

    @Override
    public void delete() throws IOException {
      this.request.delete();
    }

    @Override
    public boolean equals(final Object other) {
      if(this == other) {
        return true;
      }
      if(other == null || this.getClass() != other.getClass()) {
        return false;
      }
      final Impl that = (Impl) other;
      return Objects.equals(this.url, that.url)
        && Objects.equals(this.name, that.name)
        && Objects.equals(this.color, that.color);
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
}
