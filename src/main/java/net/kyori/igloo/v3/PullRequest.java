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

import com.google.gson.annotations.SerializedName;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A pull request in a {@link Repository repository}.
 */
public interface PullRequest {
  /**
   * Gets the number.
   *
   * @return the number
   */
  int number();

  /**
   * Gets the html url.
   *
   * @return the html url
   */
  @NonNull String html_url();

  /**
   * Gets the title.
   *
   * @return the title
   */
  @NonNull String title();

  /**
   * Gets the body.
   *
   * @return the body
   */
  @NonNull String body();

  /**
   * Gets the state.
   *
   * @return the state
   */
  @NonNull State state();

  /**
   * Checks if the pull request is merged.
   *
   * @return {@code true} if the pull request is merged, {@code false} otherwise
   */
  boolean merged();

  /**
   * The state of a pull request.
   */
  enum State {
    @SerializedName("open")
    OPEN,
    @SerializedName("closed")
    CLOSED;
  }
}
