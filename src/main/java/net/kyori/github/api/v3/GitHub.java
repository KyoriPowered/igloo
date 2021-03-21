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
package net.kyori.github.api.v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequest;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.kyori.github.api.v3.implementation.GitHubImpl;
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
    return new GitHubImpl.BuilderImpl();
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
     * Sets the object mapper instance.
     *
     * @param json the gson instance
     * @return the builder
     */
    @NonNull Builder json(final @NonNull ObjectMapper json);

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
    default @NonNull Builder authToken(final @NonNull Supplier<String> token) {
      return this.auth(() -> "token " + token.get());
    }

    /**
     * Sets the api authentication token.
     *
     * @param token the api authentication token
     * @return the builder
     */
    default @NonNull Builder authToken(final @NonNull String token) {
      return this.authToken(() -> token);
    }

    /**
     * Sets the HTTP request configurer.
     *
     * @param configurer the configurer
     * @return the builder
     */
    @NonNull Builder http(final @NonNull Consumer<HttpRequest> configurer);

    /**
     * Sets the api endpoint.
     *
     * @param endpoint the api endpoint
     * @return the builder
     */
    @NonNull Builder endpoint(final @NonNull String endpoint);

    /**
     * Builds.
     *
     * @return github api
     */
    @NonNull GitHub build();
  }
}
