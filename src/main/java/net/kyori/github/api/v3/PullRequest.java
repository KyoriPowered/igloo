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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A pull request in a {@link Repository repository}.
 *
 * @since 2.0.0
 */
public interface PullRequest {
  /**
   * Gets the number.
   *
   * @return the number
   * @since 2.0.0
   */
  int number();

  /**
   * Gets the html url.
   *
   * @return the html url
   * @since 2.0.0
   */
  @NonNull String html_url();

  /**
   * Gets the user.
   *
   * @return the user
   * @since 2.0.0
   */
  @NonNull User user();

  /**
   * Gets the title.
   *
   * @return the title
   * @since 2.0.0
   */
  @NonNull String title();

  /**
   * Gets the body.
   *
   * @return the body
   * @since 2.0.0
   */
  @NonNull String body();

  /**
   * Gets the state.
   *
   * @return the state
   * @since 2.0.0
   */
  @NonNull State state();

  /**
   * Checks if the pull request is merged.
   *
   * @return {@code true} if the pull request is merged, {@code false} otherwise
   * @since 2.0.0
   */
  boolean merged();

  /**
   * Pull request reviews.
   *
   * @return pull request reviews
   * @since 2.0.0
   */
  @NonNull PullRequestReviews reviews();

  /**
   * The state of a pull request.
   *
   * @since 2.0.0
   */
  enum State {
    @JsonProperty("open")
    OPEN,
    @JsonProperty("closed")
    CLOSED;
  }
}
