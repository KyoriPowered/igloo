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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Partials that make up a full label.
 *
 * @since 2.0.0
 */
public interface LabelPartial {
  /**
   * The name of a label.
   *
   * @since 2.0.0
   */
  interface NamePartial extends LabelPartial {
    /**
     * Gets the label's name.
     *
     * @return the label name
     * @since 2.0.0
     */
    @JsonProperty
    @NonNull String name();
  }

  /**
   * The color of a label.
   *
   * @since 2.0.0
   */
  interface ColorPartial extends LabelPartial {
    /**
     * Gets the issue's color.
     *
     * @return the label color
     * @since 2.0.0
     */
    @JsonProperty
    @NonNull String color();
  }

  /**
   * The description of a label.
   *
   * @since 2.0.0
   */
  interface DescriptionPartial extends LabelPartial {
    /**
     * Gets the issue's description.
     *
     * @return the label description
     * @since 2.0.0
     */
    @JsonProperty
    @Nullable String description();
  }
}
