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
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A pull request review.
 *
 * @since 2.0.0
 */
public interface PullRequestReview {
  /**
   * Gets the user who submitted the review.
   *
   * @return the user who submitted the review
   * @since 2.0.0
   */
  @NonNull User user();

  /**
   * Gets the state of the review.
   *
   * @return the state of the review
   * @since 2.0.0
   */
  @NonNull State state();

  /**
   * Gets the body of the review.
   *
   * @return the body of the review
   * @since 2.0.0
   */
  @NonNull String body();

  /**
   * A document used to create a pull request review.
   *
   * @since 2.0.0
   */
  interface Create {
    /**
     * Gets the event.
     *
     * @return the event
     * @since 2.0.0
     */
    @NonNull Event event();
  }

  /**
   * A document used to create a pull request review.
   *
   * @since 2.0.0
   */
  interface CreateWithBody extends Create {
    /**
     * Gets the body.
     *
     * @return the body
     * @since 2.0.0
     */
    @Nullable String body();
  }

  /**
   * The type of a review, when creating.
   *
   * @since 2.0.0
   */
  enum Event {
    /*@SerializedName("PENDING")
    PENDING,*/ // We do not support sending PENDING reviews currently
    @JsonProperty("APPROVE")
    APPROVE,
    @JsonProperty("REQUEST_CHANGES")
    REQUEST_CHANGES,
    @JsonProperty("COMMENT")
    COMMENT;
  }

  /**
   * The state of a review.
   *
   * @since 2.0.0
   */
  enum State {
    @JsonProperty("APPROVED")
    APPROVED,
    @JsonProperty("CHANGES_REQUESTED")
    CHANGES_REQUESTED,
  }
}
