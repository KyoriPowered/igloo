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

/**
 * Provides access to v3 of the GitHub API.
 */
public interface GitHub {
  /**
   * The endpoint for v3 of the GitHub API.
   */
  String API_ENDPOINT = "https://api.github.com";

  /**
   * Creates a new builder.
   *
   * @return a new builder
   */
  static @NonNull Builder builder() {
    return new Builder.Impl();
  }

  /**
   * Gets repositories.
   *
   * @return repositories
   */
  @NonNull Repositories repositories();

  /**
   * Gets users.
   *
   * @return users
   */
  @NonNull Users users();

  /**
   * A builder for creating instances of the GitHub API.
   */
  interface Builder {
    /**
     * Sets the gson instance.
     *
     * @param gson the gson instance
     * @return the builder
     */
    @NonNull Builder gson(final @NonNull Gson gson);

    /**
     * Sets the api authentication token.
     *
     * @param token the api authentication token
     * @return the builder
     */
    @NonNull Builder token(final @NonNull String token);

    /**
     * Builds.
     *
     * @return github api
     */
    @NonNull GitHub build();

    final class Impl implements Builder {
      private Gson gson;
      private String token;

      @Override
      public @NonNull Builder gson(final @NonNull Gson gson) {
        this.gson = gson;
        return this;
      }

      @Override
      public @NonNull Builder token(final @NonNull String token) {
        this.token = token;
        return this;
      }

      @Override
      public @NonNull GitHub build() {
        return new GitHub.Impl(this.gson, this.token);
      }
    }
  }

  final class Impl implements GitHub {
    private final Request request;

    private Impl(final Gson gson, final String authentication) {
      final HttpRequestFactory factory = new ApacheHttpTransport().createRequestFactory((request) -> {
        request.setIOExceptionHandler(new HttpBackOffIOExceptionHandler(new ExponentialBackOff()));
        request.setNumberOfRetries(10);
        final HttpHeaders headers = request.getHeaders();
        headers.setAccept("application/vnd.github.v3+json");
        headers.setContentType(Json.MEDIA_TYPE);
        headers.setAuthorization("token " + authentication);
        headers.setUserAgent("igloo");
      });
      this.request = new Request.Impl(gson, factory, new Request.Url(API_ENDPOINT));
    }

    @Override
    public @NonNull Repositories repositories() {
      return new Repositories.Impl(this.request);
    }

    @Override
    public @NonNull Users users() {
      return new Users.Impl();
    }
  }
}
