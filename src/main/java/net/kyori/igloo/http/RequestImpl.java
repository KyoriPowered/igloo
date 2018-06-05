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

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.json.Json;
import com.google.common.base.MoreObjects;
import com.google.gson.Gson;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class RequestImpl implements Request {
  final Gson gson;
  final HttpRequestFactory factory;
  private final Url url;

  public RequestImpl(final Gson gson, final HttpRequestFactory factory, final Url url) {
    this.gson = gson;
    this.factory = factory;
    this.url = url;
  }

  @Override
  public @NonNull Request path(final @NonNull String... path) {
    return new RequestImpl(this.gson, this.factory, new Url(this.url, path));
  }

  @Override
  public @NonNull Request up(final int n) {
    return new RequestImpl(this.gson, this.factory, new Url(this.url, n));
  }

  @Override
  public Response get() throws IOException {
    return this.response(this.factory.buildGetRequest(this.url));
  }

  @Override
  public Response post(final Object content) throws IOException {
    return this.response(this.factory.buildPostRequest(this.url, this.content(content)));
  }

  @Override
  public Response patch(final Object content) throws IOException {
    return this.response(this.factory.buildPatchRequest(this.url, this.content(content)));
  }

  @Override
  public Response put(final Object content) throws IOException {
    return this.response(this.factory.buildPutRequest(this.url, this.content(content)));
  }

  @Override
  public Response delete() throws IOException {
    return this.response(this.factory.buildDeleteRequest(this.url));
  }

  private HttpContent content(final Object object) {
    if(object instanceof HttpContent) {
      return (HttpContent) object;
    }
    return new ByteArrayContent(Json.MEDIA_TYPE, this.gson.toJson(object).getBytes(StandardCharsets.UTF_8));
  }

  private Response response(final HttpRequest request) throws IOException {
    return new ResponseImpl(this, request.execute());
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("url", this.url)
      .toString();
  }
}
