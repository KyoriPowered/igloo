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

import com.google.api.client.http.HttpResponse;
import com.google.common.reflect.TypeToken;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;

final class ResponseImpl implements Response {
  private static final Pattern LINK_PATTERN = Pattern.compile("<([^>]+)>\\s*;(.*)");
  private static final Pattern COMMA_PATTERN = Pattern.compile(",");
  private static final Pattern EQUALS_PATTERN = Pattern.compile("=");
  private final RequestImpl request;
  private final HttpResponse response;

  ResponseImpl(final RequestImpl request, final HttpResponse response) {
    this.request = request;
    this.response = response;
  }

  @Override
  public <R> R as(final TypeToken<R> type) throws IOException {
    try {
      return (R) this.request.gson.fromJson(this.response.parseAsString(), type.getType());
    } finally {
      this.close();
    }
  }

  @Override
  public Link link() {
    final String header = this.response.getHeaders().getFirstHeaderStringValue("Link");
    if(header == null) {
      return EmptyLink.INSTANCE;
    }
    return new LinkImpl(header);
  }

  @Override
  public void close() throws IOException {
    this.response.disconnect();
  }

  enum EmptyLink implements Link {
    INSTANCE;

    @Override
    public Optional<Request> previous() {
      return Optional.empty();
    }

    @Override
    public Optional<Request> next() {
      return Optional.empty();
    }
  }

  final class LinkImpl implements Link {
    private final Request previous;
    private final Request next;

    LinkImpl(final String header) {
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
        this.request = new RequestImpl(ResponseImpl.this.request.gson, ResponseImpl.this.request.factory, new Request.Url(matcher.group(1)));
        this.rel = EQUALS_PATTERN.split(matcher.group(2))[1].trim().replace("\"", "");
      }
    }
  }
}
