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

import com.google.api.client.http.EmptyContent;
import com.google.api.client.json.GenericJson;
import com.google.gson.annotations.SerializedName;
import net.kyori.blizzard.NonNull;
import net.kyori.blizzard.Nullable;
import net.kyori.cereal.Document;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;

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
  @NonNull
  String html_url();

  /**
   * Submits an edit to the issue.
   *
   * @param edit the edit
   * @param <E> the edit type
   * @throws IOException if an exception occurs during edit
   */
  <E extends Edit> void edit(@NonNull final E edit) throws IOException;

  /**
   * Gets the labels.
   *
   * @return the labels
   */
  @NonNull
  Labels.Issue labels();

  /**
   * Gets the comments.
   *
   * @return the comments
   */
  @NonNull
  Comments comments();

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
   * A document that can be submitted during issue creation.
   */
  interface Create extends Document, Partial {
    /**
     * A document containing all information that may be submitted during creation.
     */
    interface Full extends Create, Partial.Title, Partial.Body, Partial.Milestone, Partial.Labels, Partial.Assignees {
    }
  }

  /**
   * A document that can be submitted during an issue edit.
   */
  interface Edit extends Document, Partial {
    /**
     * A document containing all information that may be submitted during an edit.
     */
    interface Full extends Edit, Partial.Title, Partial.Body, Partial.State, Partial.Milestone, Partial.Labels, Partial.Assignees {
    }
  }

  /**
   * Partial documents used during issue creation and edits.
   */
  interface Partial {
    /**
     * A document representing an issue's title.
     */
    interface Title extends Create, Edit {
      /**
       * Gets the issue's title.
       *
       * @return the issue title
       */
      @Nullable
      String title();
    }

    /**
     * A document representing an issue's body.
     */
    interface Body extends Create, Edit {
      /**
       * Gets the issue's body.
       *
       * @return the issue body
       */
      @Nullable
      String body();
    }

    /**
     * A document representing an issue's state.
     */
    interface State extends Edit {
      /**
       * Gets the issue's state.
       *
       * @return the issue state
       */
      @Nullable
      Issue.State state();
    }

    /**
     * A document representing an issue's milestone.
     */
    interface Milestone extends Create, Edit {
      /**
       * Gets the issue's milestone.
       *
       * @return the issue milestone
       */
      int milestone();
    }

    /**
     * A document representing an issue's labels.
     */
    interface Labels extends Create, Edit {
      /**
       * Gets the issue's labels.
       *
       * @return the issue's labels
       */
      @Nullable
      Collection<String> labels();
    }

    /**
     * A document representing an issue's assignees.
     */
    interface Assignees extends Create, Edit {
      /**
       * Gets the issue's assignees.
       *
       * @return the issue's assignees
       */
      @Nullable
      Collection<String> assignees();
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

  abstract class Abstract implements Issue {
    final Request request;
    private final int number;

    Abstract(final Request request, final int number) {
      this.request = request.path(Integer.toString(number));
      this.number = number;
    }

    @Override
    public int number() {
      return this.number;
    }

    @Override
    public <E extends Edit> void edit(@NonNull final E edit) throws IOException {
      this.request.patch(edit);
    }

    @NonNull
    @Override
    public Labels.Issue labels() {
      return new Labels.Issue.Impl(this.request);
    }

    @NonNull
    @Override
    public Comments comments() {
      return new Comments.Impl(this.request);
    }

    @Override
    public void lock() throws IOException {
      this.request.path("lock").put(new EmptyContent());
    }

    @Override
    public void unlock() throws IOException {
      this.request.path("lock").delete();
    }
  }

  final class Impl extends Abstract {
    private final Supplier<GenericJson> json;

    Impl(final Request request, final int number) {
      super(request, number);
      this.json = Request.getAsJson(this.request);
    }

    @NonNull
    @Override
    public String html_url() {
      return (String) this.json.get().get("html_url");
    }

    static final class Created extends Abstract {
      private final String html_url;

      Created(final Request request, final int number, final String html_url) {
        super(request, number);
        this.html_url = html_url;
      }

      @NonNull
      @Override
      public String html_url() {
        return this.html_url;
      }
    }
  }
}
