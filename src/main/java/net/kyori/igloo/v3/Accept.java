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

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.Streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface Accept {
  /**
   * The header name.
   */
  String HEADER_NAME = "Accept";

  /**
   * The standard values.
   */
  List<String> HEADER_VALUES = Streams.concat(
    Stream.of(
      // GraphQL Global Relay IDs
      "application/vnd.github.jean-grey-preview+json",
      // Label emoji, search, and descriptions
      "application/vnd.github.symmetra-preview+json"
    ),
    Stream.of("application/vnd.github.v3+json")
  ).collect(Collectors.toList());

  static void add(final HttpRequest request, final String accept) {
    add(request, Collections.singletonList(accept));
  }

  static void add(final HttpRequest request, final List<String> accept) {
    final HttpHeaders headers = request.getHeaders();
    final List<String> result = new ArrayList<>(headers.getHeaderStringValues(HEADER_NAME));
    result.addAll(accept);
    headers.put(HEADER_NAME, result);
  }
}
