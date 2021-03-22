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

import java.io.IOException;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A label that may be applied to an {@link Issue}.
 *
 * @since 2.0.0
 */
public interface Label {
  /**
   * Gets the url.
   *
   * @return the url
   * @since 2.0.0
   */
  @NonNull String url();

  /**
   * Gets the name.
   *
   * @return the name
   * @since 2.0.0
   */
  @NonNull String name();

  /**
   * Gets the description.
   *
   * @return the description
   * @since 2.0.0
   */
  @NonNull String description();

  /**
   * Gets the color.
   *
   * @return the color
   * @since 2.0.0
   */
  @NonNull String color();

  /**
   * Submits an edit to the label.
   *
   * @param edit the edit
   * @param <E> the edit type
   * @throws IOException if an exception occurs during edit
   * @since 2.0.0
   */
  <E extends Edit> void edit(final @NonNull E edit) throws IOException;

  /**
   * Deletes the label.
   *
   * @throws IOException if an exception occurs during edit
   * @since 2.0.0
   */
  void delete() throws IOException;

  /**
   * A document that can be submitted during label creation.
   *
   * @since 2.0.0
   */
  interface Create extends LabelPartial.NamePartial, LabelPartial.ColorPartial {
    /**
     * A document containing all information that may be submitted during creation.
     *
     * @since 2.0.0
     */
    interface Full extends Create, LabelPartial.DescriptionPartial {
    }
  }

  /**
   * A document that can be submitted during a label edit.
   *
   * @since 2.0.0
   */
  interface Edit extends LabelPartial {
    /**
     * A document containing all information that may be submitted during an edit.
     *
     * @since 2.0.0
     */
    interface Full extends Edit, NamePartial, ColorPartial, DescriptionPartial {
    }
  }
}
