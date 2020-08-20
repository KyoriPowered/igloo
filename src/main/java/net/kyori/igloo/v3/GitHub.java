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

import com.google.api.client.http.HttpRequest;
import com.google.gson.Gson;
import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

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
   * Gets organizations.
   *
   * @return organizations
   */
  @NonNull Organizations orgs();

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
     * Sets the api endpoint.
     *
     * @param endpoint the api endpoint
     * @return the builder
     */
    @NonNull Builder endpoint(final @NonNull String endpoint);

    /**
     * Sets the api authentication string.
     *
     * @param auth the api authentication string
     * @return the builder
     */
    default @NonNull Builder auth(final @NonNull String auth) {
      return this.auth(() -> auth);
    }

    /**
     * Sets the api authentication string.
     *
     * @param auth the api authentication string
     * @return the builder
     */
    @NonNull Builder auth(final @NonNull Supplier<String> auth);

    /**
     * Sets the api authentication token.
     *
     * @param token the api authentication token
     * @return the builder
     */
    default @NonNull Builder token(final @NonNull Supplier<String> token) {
      return this.auth(() -> "token " + token.get());
    }

    /**
     * Sets the api authentication token.
     *
     * @param token the api authentication token
     * @return the builder
     */
    default @NonNull Builder token(final @NonNull String token) {
      return this.token(() -> token);
    }

    /**
     * Sets the HTTP request configurer.
     *
     * @param configurer the configurer
     * @return the builder
     */
    @NonNull Builder http(final @NonNull Consumer<HttpRequest> configurer);

    /**
     * Builds.
     *
     * @return github api
     */
    @NonNull GitHub build();

    final class Impl implements Builder {
      private Gson gson;
      private String endpoint = API_ENDPOINT;
      private @Nullable Supplier<String> auth;
      private @Nullable Consumer<HttpRequest> httpRequestConfigurer;

      @Override
      public @NonNull Builder gson(final @NonNull Gson gson) {
        this.gson = gson;
        return this;
      }

      @Override
      public @NonNull Builder endpoint(final @NonNull String endpoint) {
        this.endpoint = endpoint;
        return this;
      }

      @Override
      public @NonNull Builder auth(final @NonNull Supplier<String> auth) {
        this.auth = auth;
        return this;
      }

      @Override
      public @NonNull Builder http(final @NonNull Consumer<HttpRequest> configurer) {
        this.httpRequestConfigurer = configurer;
        return this;
      }

      @Override
      public @NonNull GitHub build() {
        requireNonNull(this.gson, "gson");
        return new GitHubImpl(this.gson, this.endpoint, this.auth, this.httpRequestConfigurer);
      }
    }
  }
}
