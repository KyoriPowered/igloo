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

import com.google.api.client.http.HttpBackOffIOExceptionHandler;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.Json;
import com.google.api.client.util.ExponentialBackOff;
import com.google.gson.Gson;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.List;

final class GitHubImpl implements GitHub {
  private static final List<String> ACCEPT_HEADERS = Arrays.asList(
    "application/vnd.github.jean-grey-preview+json",
    "application/vnd.github.symmetra-preview+json",
    "application/vnd.github.v3+json"
  );
  private final Request request;

  GitHubImpl(final Gson gson, final String endpoint, final @Nullable String authentication) {
    final HttpRequestFactory factory = new ApacheHttpTransport().createRequestFactory((request) -> {
      request.setIOExceptionHandler(new HttpBackOffIOExceptionHandler(new ExponentialBackOff()));
      request.setNumberOfRetries(10);
      final HttpHeaders headers = request.getHeaders();
      headers.put("Accept", ACCEPT_HEADERS);
      headers.setContentType(Json.MEDIA_TYPE);
      if(authentication != null) {
        headers.setAuthorization("token " + authentication);
      }
      headers.setUserAgent("igloo");
    });
    this.request = new RequestImpl(gson, factory, new Request.Url(endpoint));
  }

  @Override
  public @NonNull Repositories repositories() {
    return new RepositoriesImpl(this.request);
  }

  @Override
  public @NonNull Users users() {
    return new UsersImpl();
  }
}
