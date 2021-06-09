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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.client.http.HttpBackOffIOExceptionHandler;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.api.client.json.Json;
import com.google.api.client.util.ExponentialBackOff;
import com.google.common.collect.Streams;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.kyori.github.api.v3.GitHub;
import net.kyori.github.api.v3.GitHubApp;
import net.kyori.github.api.v3.Organizations;
import net.kyori.github.api.v3.Repositories;
import net.kyori.github.api.v3.Users;
import net.kyori.github.api.v3.auth.AuthorizationSource;
import net.kyori.github.util.Accept;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * GitHub API.
 *
 * @since 2.0.0
 */
public final class GitHubImpl implements GitHub {
  /**
   * The standard values.
   */
  private static final List<String> HEADER_VALUES = Streams.concat(
    Stream.of(
      // GraphQL Global Relay IDs
      "application/vnd.github.jean-grey-preview+json",
      // Label emoji, search, and descriptions
      "application/vnd.github.symmetra-preview+json"
    ),
    Stream.of("application/vnd.github.v3+json")
  ).collect(Collectors.toList());
  private final HTTP.RequestTemplate request;

  GitHubImpl(final String endpoint, final @Nullable AuthorizationSource auth, final @Nullable Consumer<HttpRequest> httpRequestConfigurer) {
    final HttpRequestFactory factory = new ApacheHttpTransport().createRequestFactory(request -> {
      request.setIOExceptionHandler(new HttpBackOffIOExceptionHandler(new ExponentialBackOff()));
      request.setNumberOfRetries(10);
      if(httpRequestConfigurer != null) {
        httpRequestConfigurer.accept(request);
      }
      final HttpHeaders headers = request.getHeaders();
      headers.put(Accept.HEADER_NAME, HEADER_VALUES);
      headers.setContentType(Json.MEDIA_TYPE);
      if(auth != null) {
        headers.setAuthorization(auth.currentAuthorization());
      }
      if(headers.getUserAgent() == null) {
        headers.setUserAgent("igloo");
      }
    });
    final ObjectMapper json = JsonMapper.builder()
      .addModule(new JavaTimeModule())
      .build();
    this.request = new HTTP.RequestTemplate(json, factory, new HTTP.Url(endpoint));
  }

  @Override
  public @NonNull Repositories repositories() {
    return new RepositoriesImpl(this.request);
  }

  @Override
  public @NonNull Users users() {
    return new UsersImpl(this.request);
  }

  @Override
  public @NonNull Organizations orgs() {
    return new OrganizationsImpl(this.request);
  }

  @Override
  public @NonNull GitHubApp app() {
    return new GitHubAppImpl(this.request);
  }

  /**
   * GitHub API builder.
   *
   * @since 2.0.0
   */
  public static final class BuilderImpl implements Builder {
    private String endpoint = API_ENDPOINT;
    private @Nullable AuthorizationSource auth;
    private @Nullable Consumer<HttpRequest> httpRequestConfigurer;

    @Override
    public @NonNull Builder endpoint(final @NonNull String endpoint) {
      this.endpoint = endpoint;
      return this;
    }

    @Override
    public @NonNull Builder auth(final @NonNull AuthorizationSource auth) {
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
      return new GitHubImpl(this.endpoint, this.auth, this.httpRequestConfigurer);
    }
  }
}
