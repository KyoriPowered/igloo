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
package net.kyori.github.api.v3.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.Json;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.reflect.TypeToken;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.NonNull;

import static com.google.common.base.Preconditions.checkState;

final class HTTP {
  static final class Url extends GenericUrl {
    private static final Joiner JOINER = Joiner.on('/');

    Url(final String url) {
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

  static final class RequestTemplate {
    final ObjectMapper json;
    final HttpRequestFactory requests;
    private final Url url;

    RequestTemplate(final ObjectMapper json, final HttpRequestFactory requests, final Url url) {
      this.json = json;
      this.requests = requests;
      this.url = url;
    }

    public RequestTemplate path(final @NonNull String path) {
      return new RequestTemplate(this.json, this.requests, new Url(this.url, path));
    }

    public RequestTemplate path(final @NonNull String... path) {
      return new RequestTemplate(this.json, this.requests, new Url(this.url, path));
    }

    public RequestTemplate up(final int n) {
      return new RequestTemplate(this.json, this.requests, new Url(this.url, n));
    }

    public Response get() throws IOException {
      return this.response(this.requests.buildGetRequest(this.url));
    }

    public Response post(final Object content) throws IOException {
      return this.response(this.requests.buildPostRequest(this.url, this.content(content)));
    }

    public Response patch(final Object content) throws IOException {
      return this.response(this.requests.buildPatchRequest(this.url, this.content(content)));
    }

    public Response put(final Object content) throws IOException {
      return this.response(this.requests.buildPutRequest(this.url, this.content(content)));
    }

    public Response delete() throws IOException {
      return this.response(this.requests.buildDeleteRequest(this.url));
    }

    private HttpContent content(final Object object) throws JsonProcessingException {
      if(object instanceof HttpContent) {
        return (HttpContent) object;
      }
      return new ByteArrayContent(Json.MEDIA_TYPE, this.json.writeValueAsString(object).getBytes(StandardCharsets.UTF_8));
    }

    private Response response(final HttpRequest request) throws IOException {
      return new Response(this, request.execute());
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
        .add("url", this.url)
        .toString();
    }
  }

  static final class Response implements AutoCloseable {
    private final RequestTemplate request;
    private final HttpResponse response;

    Response(final RequestTemplate request, final HttpResponse response) {
      this.request = request;
      this.response = response;
    }

    <R> R as(final Class<R> type) throws IOException {
      return this.as(TypeToken.of(type));
    }

    <R> R as(final TypeToken<R> type) throws IOException {
      try {
        return this.request.json.readValue(this.response.parseAsString(), this.request.json.getTypeFactory().constructType(type.getType()));
      } finally {
        this.close();
      }
    }

    public @NonNull Link link() {
      final String header = this.response.getHeaders().getFirstHeaderStringValue("Link");
      if(header == null) {
        return Link.Empty.INSTANCE;
      }
      return new Link.Impl(this.request, header);
    }

    @Override
    public void close() throws IOException {
      this.response.disconnect();
    }
  }

  public interface Link {
    Optional<RequestTemplate> previous();

    Optional<RequestTemplate> next();

    enum Empty implements Link {
      INSTANCE;

      @Override
      public Optional<RequestTemplate> previous() {
        return Optional.empty();
      }

      @Override
      public Optional<RequestTemplate> next() {
        return Optional.empty();
      }
    }

    final class Impl implements Link {
      private static final Pattern LINK_PATTERN = Pattern.compile("<([^>]+)>\\s*;(.*)");
      private static final Pattern COMMA_PATTERN = Pattern.compile(",");
      private static final Pattern EQUALS_PATTERN = Pattern.compile("=");
      private final RequestTemplate request;
      private final RequestTemplate previous;
      private final RequestTemplate next;

      Impl(final RequestTemplate request, final String header) {
        this.request = request;
        final Map<String, RequestTemplate> parts = Arrays.stream(COMMA_PATTERN.split(header))
          .map(String::trim)
          .map(LINK_PATTERN::matcher)
          .map(Part::new)
          .collect(Collectors.toMap(part -> part.rel, part -> part.request));
        this.previous = parts.get("prev");
        this.next = parts.get("next");
      }

      @Override
      public Optional<RequestTemplate> previous() {
        return Optional.ofNullable(this.previous);
      }

      @Override
      public Optional<RequestTemplate> next() {
        return Optional.ofNullable(this.next);
      }

      final class Part {
        final String rel;
        final RequestTemplate request;

        Part(final Matcher matcher) {
          checkState(matcher.matches());
          this.request = new RequestTemplate(Impl.this.request.json, Impl.this.request.requests, new Url(matcher.group(1)));
          this.rel = EQUALS_PATTERN.split(matcher.group(2))[1].trim().replace("\"", "");
        }
      }
    }
  }
}
