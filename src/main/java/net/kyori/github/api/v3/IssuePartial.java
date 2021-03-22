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
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Partial documents used during issue creation and edits.
 *
 * @since 2.0.0
 */
public interface IssuePartial {
  /**
   * A document representing an issue's title.
   *
   * @since 2.0.0
   */
  interface TitlePartial extends Issue.AbstractCreate, Issue.Edit, IssuePartial {
    /**
     * Gets the issue's title.
     *
     * @return the issue title
     * @since 2.0.0
     */
    @JsonProperty
    @Nullable String title();
  }

  /**
   * A document representing an issue's body.
   *
   * @since 2.0.0
   */
  interface BodyPartial extends Issue.Create, Issue.Edit, IssuePartial {
    /**
     * Gets the issue's body.
     *
     * @return the issue body
     * @since 2.0.0
     */
    @JsonProperty
    @Nullable String body();
  }

  /**
   * A document representing an issue's state.
   *
   * @since 2.0.0
   */
  interface StatePartial extends Issue.Edit, IssuePartial {
    /**
     * Gets the issue's state.
     *
     * @return the issue state
     * @since 2.0.0
     */
    @JsonProperty
    Issue.@Nullable State state();
  }

  /**
   * A document representing an issue's milestone.
   *
   * @since 2.0.0
   */
  interface MilestonePartial extends Issue.Create, Issue.Edit, IssuePartial {
    /**
     * Gets the issue's milestone.
     *
     * @return the issue milestone
     * @since 2.0.0
     */
    @JsonProperty("milestone")
    int milestone();
  }

  /**
   * A document representing an issue's labels.
   *
   * @since 2.0.0
   */
  interface LabelsPartial extends Issue.Create, Issue.Edit, IssuePartial {
    /**
     * Gets the issue's labels.
     *
     * @return the issue's labels
     * @since 2.0.0
     */
    @JsonProperty("labels")
    @Nullable Collection<String> labels();
  }

  /**
   * A document representing an issue's assignees.
   *
   * @since 2.0.0
   */
  interface AssigneesPartial extends Issue.Create, Issue.Edit, IssuePartial {
    /**
     * Gets the issue's assignees.
     *
     * @return the issue's assignees
     * @since 2.0.0
     */
    @JsonProperty("assignees")
    @Nullable Collection<String> assignees();
  }
}
