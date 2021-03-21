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
package net.kyori.github.util.http;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;

final class LinkImpl implements Link {
  private static final Pattern LINK_PATTERN = Pattern.compile("<([^>]+)>\\s*;(.*)");
  private static final Pattern COMMA_PATTERN = Pattern.compile(",");
  private static final Pattern EQUALS_PATTERN = Pattern.compile("=");
  private final RequestImpl request;
  private final Request previous;
  private final Request next;

  LinkImpl(final RequestImpl request, final String header) {
    this.request = request;
    final Map<String, Request> parts = Arrays.stream(COMMA_PATTERN.split(header))
      .map(String::trim)
      .map(LINK_PATTERN::matcher)
      .map(Part::new)
      .collect(Collectors.toMap(part -> part.rel, part -> part.request));
    this.previous = parts.get("prev");
    this.next = parts.get("next");
  }

  @Override
  public Optional<Request> previous() {
    return Optional.ofNullable(this.previous);
  }

  @Override
  public Optional<Request> next() {
    return Optional.ofNullable(this.next);
  }

  final class Part {
    final String rel;
    final Request request;

    Part(final Matcher matcher) {
      checkState(matcher.matches());
      this.request = new RequestImpl(LinkImpl.this.request.json, LinkImpl.this.request.factory, new Request.Url(matcher.group(1)));
      this.rel = EQUALS_PATTERN.split(matcher.group(2))[1].trim().replace("\"", "");
    }
  }
}
