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
import java.util.Collections;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * An issue's labels.
 *
 * @since 2.0.0
 */
public interface IssueLabels extends Labels {
  /**
   * Add a label to the issue.
   *
   * @param name the label name
   * @throws IOException if an exception occurs while adding the label
   * @since 2.0.0
   */
  default void add(final @NonNull String name) throws IOException {
    this.add(Collections.singleton(name));
  }

  /**
   * Add labels to the issue.
   *
   * @param names the label names
   * @throws IOException if an exception occurs while adding the labels
   * @since 2.0.0
   */
  void add(final @NonNull Iterable<String> names) throws IOException;

  /**
   * Set an issues labels.
   *
   * @param names the label names
   * @throws IOException if an exception occurs while setting the labels
   * @since 2.0.0
   */
  void set(final @NonNull Iterable<String> names) throws IOException;

  /**
   * Remove a label from the issue.
   *
   * @param name the label name
   * @throws IOException if an exception occurs while removing the label
   * @since 2.0.0
   */
  void remove(final @NonNull String name) throws IOException;
}
