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

import net.kyori.blizzard.NonNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Labels.
 */
public interface Labels {
  /**
   * Gets all the labels.
   *
   * @return all the labels
   * @throws IOException if an exception occured while getting the labels
   */
  @NonNull
  Iterable<Label> all() throws IOException;

  /**
   * A repository's labels.
   */
  interface Repository extends Labels {
    /**
     * Creates a new label.
     *
     * @param create the creation data
     * @param <C> the creation data type
     * @return the new label
     * @throws IOException if an exception occurs while creating a new issue
     */
    @NonNull
    <C extends Label.Create & Label.Partial.Name & Label.Partial.Color> Label create(@NonNull final C create) throws IOException;

    final class Impl implements Repository {
      private final Request request;

      Impl(final Request request) {
        this.request = request.path("labels");
      }

      @NonNull
      @Override
      public Iterable<Label> all() throws IOException {
        return Arrays.stream(this.request.get(Partial.Label[].class))
          .map(label -> new Label.Impl(this.request, label.url, label.name, label.color))
          .collect(Collectors.toSet());
      }

      @NonNull
      @Override
      public <C extends Label.Create & Label.Partial.Name & Label.Partial.Color> Label create(@NonNull final C create) throws IOException {
        final Partial.Label label = this.request.post(create, Partial.Label.class);
        return new Label.Impl(this.request, label.url, label.name, label.color);
      }
    }
  }

  /**
   * An issue's labels.
   */
  interface Issue extends Labels {
    /**
     * Add a label to the issue.
     *
     * @param name the label name
     * @throws IOException if an exception occurs while adding the label
     */
    default void add(@NonNull final String name) throws IOException {
      this.add(Collections.singleton(name));
    }

    /**
     * Add labels to the issue.
     *
     * @param names the label names
     * @throws IOException if an exception occurs while adding the labels
     */
    void add(@NonNull final Iterable<String> names) throws IOException;

    /**
     * Set an issues labels.
     *
     * @param names the label names
     * @throws IOException if an exception occurs while setting the labels
     */
    void set(@NonNull final Iterable<String> names) throws IOException;

    /**
     * Remove a label from the issue.
     *
     * @param name the label name
     * @throws IOException if an exception occurs while removing the label
     */
    void remove(@NonNull final String name) throws IOException;

    final class Impl implements Issue {
      private final Request request;

      Impl(final Request request) {
        this.request = request.path("labels");
      }

      @NonNull
      @Override
      public Iterable<Label> all() throws IOException {
        return Arrays.stream(this.request.get(Partial.Label[].class))
          .map(label -> new Label.Impl(this.request.up(3), label.url, label.name, label.color))
          .collect(Collectors.toSet());
      }

      @Override
      public void add(@NonNull final Iterable<String> names) throws IOException {
        this.request.post(names);
      }

      @Override
      public void set(@NonNull final Iterable<String> names) throws IOException {
        this.request.put(names);
      }

      @Override
      public void remove(@NonNull final String name) throws IOException {
        this.request.delete();
      }
    }
  }
}
