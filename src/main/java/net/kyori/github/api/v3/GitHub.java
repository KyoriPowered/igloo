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

import com.google.api.client.http.HttpRequest;
import java.util.function.Consumer;
import net.kyori.github.api.v3.auth.AuthorizationSource;
import net.kyori.github.api.v3.implementation.GitHubImpl;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides access to v3 of the GitHub API.
 *
 * @since 2.0.0
 */
public interface GitHub {
  /**
   * The endpoint for v3 of the GitHub API.
   *
   * @since 2.0.0
   */
  String API_ENDPOINT = "https://api.github.com";

  /**
   * Creates a new builder.
   *
   * @return a new builder
   * @since 2.0.0
   */
  static @NonNull Builder builder() {
    return new GitHubImpl.BuilderImpl();
  }

  /**
   * Gets repositories.
   *
   * @return repositories
   * @since 2.0.0
   */
  @NonNull Repositories repositories();

  /**
   * Gets users.
   *
   * @return users
   * @since 2.0.0
   */
  @NonNull Users users();

  /**
   * Gets organizations.
   *
   * @return organizations
   * @since 2.0.0
   */
  @NonNull Organizations orgs();

  /**
   * Gets a GitHub App.
   *
   * @return a GitHub App
   * @since 2.0.0
   */
  @NonNull GitHubApp app();

  /**
   * A builder for creating instances of the GitHub API.
   *
   * @since 2.0.0
   */
  interface Builder {
    /**
     * Sets the api {@link net.kyori.github.api.v3.auth.AuthorizationSource}.
     *
     * @param auth the authorization source
     * @return the builder
     * @since 2.0.0
     */
    @NonNull Builder auth(final @NonNull AuthorizationSource auth);

    /**
     * Sets the HTTP request configurer.
     *
     * @param configurer the configurer
     * @return the builder
     * @since 2.0.0
     */
    @NonNull Builder http(final @NonNull Consumer<HttpRequest> configurer);

    /**
     * Sets the api endpoint.
     *
     * @param endpoint the api endpoint
     * @return the builder
     * @since 2.0.0
     */
    @NonNull Builder endpoint(final @NonNull String endpoint);

    /**
     * Builds.
     *
     * @return github api
     * @since 2.0.0
     */
    @NonNull GitHub build();
  }
}
