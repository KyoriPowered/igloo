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
 * Partials that make up a full label.
 */
public interface LabelPartial extends net.kyori.github.api.LabelPartial {
  /**
   * The name of a label.
   */
  interface NamePartial extends Label.Edit, LabelPartial, net.kyori.github.api.LabelPartial.NamePartial {
  }

  /**
   * The color of a label.
   */
  interface ColorPartial extends Label.Edit, LabelPartial, net.kyori.github.api.LabelPartial.ColorPartial {
  }

  /**
   * The description of a label.
   */
  interface DescriptionPartial extends Label.Edit, LabelPartial, net.kyori.github.api.LabelPartial.DescriptionPartial {
    /**
     * Gets the issue's description.
     *
     * @return the label description
     */
    @Override
    @NonNull String description();
  }
}
