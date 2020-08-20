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
package net.kyori.igloo.http;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.common.base.Joiner;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.util.List;

/**
 * A wrapper around an {@link HttpRequest http request}.
 */
public interface Request {
  /**
   * Append components to the path of the request.
   *
   * @param path the path components
   * @return a new request
   */
  @NonNull Request path(final @NonNull String path);

  /**
   * Append components to the path of the request.
   *
   * @param path the path components
   * @return a new request
   */
  @NonNull Request path(final @NonNull String... path);

  /**
   * Remove {@code n} components from the end of the path.
   *
   * @param n the levels to go up
   * @return a new request
   */
  @NonNull Request up(final int n);

  /**
   * GET.
   *
   * @return the response
   * @throws IOException if an exception occurred while getting
   */
  Response get() throws IOException;

  /**
   * POST.
   *
   * @param content the content
   * @return the response
   * @throws IOException if an exception occurred while posting
   */
  Response post(final Object content) throws IOException;

  /**
   * PATCH.
   *
   * @param content the content
   * @return the response
   * @throws IOException if an exception occurred while patching
   */
  Response patch(final Object content) throws IOException;

  /**
   * PUT.
   *
   * @param content the content
   * @return the response
   * @throws IOException if an exception occurred while putting
   */
  Response put(final Object content) throws IOException;

  /**
   * DELETE.
   *
   * @return the response
   * @throws IOException if an exception occurred while deleting
   */
  Response delete() throws IOException;

  final class Url extends GenericUrl {
    private static final Joiner JOINER = Joiner.on('/');

    public Url(final String url) {
      super(url);
    }

    Url(final Url url, final String path) {
      super(url.toString() + '/' + path);
    }

    Url(final Url url, final String... path) {
      super(url.toString() + '/' + JOINER.join(path));
    }

    Url(final Url url, final int n) {
      super(url.toString());
      final List<String> pathParts = this.getPathParts();
      this.setPathParts(pathParts.subList(0, pathParts.size() - n));
    }
  }
}
