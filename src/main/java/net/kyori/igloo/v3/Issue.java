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
import net.kyori.cereal.Document;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.util.Optional;

/**
 * An issue in a {@link Repository repository}.
 */
public interface Issue {
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
   * Submits an edit to the issue.
   *
   * @param edit the edit
   * @param <E> the edit type
   * @throws IOException if an exception occurs during edit
   */
  <E extends Edit> void edit(final @NonNull E edit) throws IOException;

  /**
   * Gets the labels.
   *
   * @return the labels
   */
  @NonNull IssueLabels labels();

  /**
   * Gets the comments.
   *
   * @return the comments
   */
  @NonNull Comments comments();

  /**
   * Locks the issue.
   *
   * @throws IOException if an exception occurs during lock
   */
  void lock() throws IOException;

  /**
   * Unlocks the issue.
   *
   * @throws IOException if an exception occurs during unlock
   */
  void unlock() throws IOException;

  /**
   * Gets the pull request.
   *
   * @return the pull request
   */
  @NonNull Optional<PullRequest> pullRequest();

  // Inheritance hack
  interface AbstractCreate extends Document, IssuePartial {
  }

  /**
   * A document that can be submitted during issue creation.
   */
  interface Create extends AbstractCreate, IssuePartial.TitlePartial {
    /**
     * A document containing all information that may be submitted during creation.
     */
    interface Full extends Create, TitlePartial, BodyPartial, MilestonePartial, LabelsPartial, AssigneesPartial {
    }
  }

  /**
   * A document that can be submitted during an issue edit.
   */
  interface Edit extends Document, IssuePartial {
    /**
     * A document containing all information that may be submitted during an edit.
     */
    interface Full extends Edit, TitlePartial, BodyPartial, StatePartial, MilestonePartial, LabelsPartial, AssigneesPartial {
    }
  }

  /**
   * The state of an issue.
   */
  enum State {
    @SerializedName("open")
    OPEN,
    @SerializedName("closed")
    CLOSED;
  }
}
