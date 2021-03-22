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
import java.io.IOException;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * An issue in a {@link Repository repository}.
 *
 * @since 2.0.0
 */
public interface Issue {
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
   * Submits an edit to the issue.
   *
   * @param edit the edit
   * @param <E> the edit type
   * @throws IOException if an exception occurs during edit
   * @since 2.0.0
   */
  <E extends Edit> void edit(final @NonNull E edit) throws IOException;

  /**
   * Gets the labels.
   *
   * @return the labels
   * @since 2.0.0
   */
  @NonNull IssueLabels labels();

  /**
   * Gets the comments.
   *
   * @return the comments
   * @since 2.0.0
   */
  @NonNull Comments comments();

  /**
   * Locks the issue.
   *
   * @throws IOException if an exception occurs during lock
   * @since 2.0.0
   */
  void lock() throws IOException;

  /**
   * Unlocks the issue.
   *
   * @throws IOException if an exception occurs during unlock
   * @since 2.0.0
   */
  void unlock() throws IOException;

  /**
   * Gets the pull request.
   *
   * @return the pull request
   * @since 2.0.0
   */
  @NonNull Optional<PullRequest> pullRequest();

  /**
   * Inheritance hack.
   *
   * @since 2.0.0
   */
  interface AbstractCreate extends IssuePartial {
  }

  /**
   * A document that can be submitted during issue creation.
   *
   * @since 2.0.0
   */
  interface Create extends AbstractCreate, IssuePartial.TitlePartial {
    /**
     * A document containing all information that may be submitted during creation.
     *
     * @since 2.0.0
     */
    interface Full extends Create, TitlePartial, BodyPartial, MilestonePartial, LabelsPartial, AssigneesPartial {
    }
  }

  /**
   * A document that can be submitted during an issue edit.
   *
   * @since 2.0.0
   */
  interface Edit extends IssuePartial {
    /**
     * A document containing all information that may be submitted during an edit.
     *
     * @since 2.0.0
     */
    interface Full extends Edit, TitlePartial, BodyPartial, StatePartial, MilestonePartial, LabelsPartial, AssigneesPartial {
    }
  }

  /**
   * The state of an issue.
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
