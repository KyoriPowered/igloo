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

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.Json;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.kyori.blizzard.NonNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
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
  @NonNull
  Request path(@NonNull final String... path);

  /**
   * Remove {@code n} components from the end of the path.
   *
   * @param n the levels to go up
   * @return a new request
   */
  @NonNull
  Request up(final int n);

  /**
   * GET.
   *
   * @param type the response type
   * @param <O> the response type
   * @return the response
   * @throws IOException if an exception occurred while getting
   */
  default <O> O get(final Class<O> type) throws IOException {
    return this.get(TypeToken.get(type));
  }

  /**
   * GET.
   *
   * @param type the response type
   * @param <O> the response type
   * @return the response
   * @throws IOException if an exception occurred while getting
   */
  <O> O get(final TypeToken<O> type) throws IOException;

  /**
   * POST.
   *
   * @param content the content
   * @throws IOException if an exception occurred while posting
   */
  void post(final Object content) throws IOException;

  /**
   * POST.
   *
   * @param content the content
   * @param type the response type
   * @param <O> the response type
   * @return the response
   * @throws IOException if an exception occurred while posting
   */
  default <O> O post(final Object content, final Class<O> type) throws IOException {
    return this.post(content, TypeToken.get(type));
  }

  /**
   * POST.
   *
   * @param content the content
   * @param type the response type
   * @param <O> the response type
   * @return the response
   * @throws IOException if an exception occurred while posting
   */
  <O> O post(final Object content, final TypeToken<O> type) throws IOException;

  /**
   * PATCH.
   *
   * @param content the content
   * @throws IOException if an exception occurred while patching
   */
  void patch(final Object content) throws IOException;

  /**
   * PATCH.
   *
   * @param content the content
   * @param type the response type
   * @param <O> the response type
   * @return the response
   * @throws IOException if an exception occurred while patching
   */
  default <O> O patch(final Object content, final Class<O> type) throws IOException {
    return this.patch(content, TypeToken.get(type));
  }

  /**
   * PATCH.
   *
   * @param content the content
   * @param type the response type
   * @param <O> the response type
   * @return the response
   * @throws IOException if an exception occurred while patching
   */
  <O> O patch(final Object content, final TypeToken<O> type) throws IOException;

  /**
   * PUT.
   *
   * @param content the content
   * @throws IOException if an exception occurred while putting
   */
  void put(final Object content) throws IOException;

  /**
   * PUT.
   *
   * @param content the content
   * @param type the response type
   * @param <O> the response type
   * @return the response
   * @throws IOException if an exception occurred while putting
   */
  default <O> O put(final Object content, final Class<O> type) throws IOException {
    return this.put(content, TypeToken.get(type));
  }

  /**
   * PUT.
   *
   * @param content the content
   * @param type the response type
   * @param <O> the response type
   * @return the response
   * @throws IOException if an exception occurred while putting
   */
  <O> O put(final Object content, final TypeToken<O> type) throws IOException;

  /**
   * DELETE.
   *
   * @throws IOException if an exception occurred while deleting
   */
  void delete() throws IOException;

  /**
   * DELETE.
   *
   * @param type the response type
   * @param <O> the response type
   * @return the response
   * @throws IOException if an exception occurred while deleting
   */
  default <O> O delete(final Class<O> type) throws IOException {
    return this.delete(TypeToken.get(type));
  }

  /**
   * DELETE.
   *
   * @param type the response type
   * @param <O> the response type
   * @return the response
   * @throws IOException if an exception occurred while deleting
   */
  <O> O delete(final TypeToken<O> type) throws IOException;

  final class Impl implements Request {
    private final Gson gson;
    private final HttpRequestFactory factory;
    private final Url url;

    Impl(final Gson gson, final HttpRequestFactory factory, final Url url) {
      this.gson = gson;
      this.factory = factory;
      this.url = url;
    }

    @NonNull
    @Override
    public Request path(@NonNull final String... path) {
      return new Impl(this.gson, this.factory, new Url(this.url, path));
    }

    @NonNull
    @Override
    public Request up(final int n) {
      return new Impl(this.gson, this.factory, new Url(this.url, n));
    }

    @Override
    public <O> O get(final TypeToken<O> type) throws IOException {
      return this.response(this.factory.buildGetRequest(this.url), type.getType());
    }

    @Override
    public void post(final Object content) throws IOException {
      this.factory.buildPostRequest(this.url, this.content(content)).execute().disconnect();
    }

    @Override
    public <O> O post(final Object content, final TypeToken<O> type) throws IOException {
      return this.response(this.factory.buildPostRequest(this.url, this.content(content)), type.getType());
    }

    @Override
    public void patch(final Object content) throws IOException {
      this.factory.buildPatchRequest(this.url, this.content(content)).execute().disconnect();
    }

    @Override
    public <O> O patch(final Object content, final TypeToken<O> type) throws IOException {
      return this.response(this.factory.buildPatchRequest(this.url, this.content(content)), type.getType());
    }

    @Override
    public void put(final Object content) throws IOException {
      this.factory.buildPutRequest(this.url, this.content(content)).execute().disconnect();
    }

    @Override
    public <O> O put(final Object content, final TypeToken<O> type) throws IOException {
      return this.response(this.factory.buildPutRequest(this.url, this.content(content)), type.getType());
    }

    @Override
    public void delete() throws IOException {
      this.factory.buildDeleteRequest(this.url).execute().disconnect();
    }

    @Override
    public <O> O delete(final TypeToken<O> type) throws IOException {
      return this.response(this.factory.buildDeleteRequest(this.url), type.getType());
    }

    private HttpContent content(final Object object) {
      if(object instanceof HttpContent) {
        return (HttpContent) object;
      }
      return new ByteArrayContent(Json.MEDIA_TYPE, this.gson.toJson(object).getBytes(StandardCharsets.UTF_8));
    }

    private <T> T response(final HttpRequest request, final Type responseType) throws IOException {
      final HttpResponse response = request.execute();
      try {
        return (T) this.gson.fromJson(response.parseAsString(), responseType);
      } finally {
        response.disconnect();
      }
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
        .add("url", this.url)
        .toString();
    }
  }

  final class Url extends GenericUrl {
    private static final Joiner JOINER = Joiner.on('/');

    Url(final String url) {
      super(url);
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
