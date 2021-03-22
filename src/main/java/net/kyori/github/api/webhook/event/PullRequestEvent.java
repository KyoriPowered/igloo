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
package net.kyori.github.api.webhook.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.kyori.github.api.webhook.model.PullRequest;
import net.kyori.github.api.webhook.model.Repository;
import net.kyori.github.api.webhook.model.User;

/**
 * PullRequestEvent.
 *
 * @since 2.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PullRequestEvent {
  public Action action;
  public PullRequest pull_request;
  public int number;
  public Repository repository;
  public User sender;

  /**
   * Action.
   *
   * @since 2.0.0
   */
  public enum Action {
    @JsonProperty("opened")
    OPENED,
    @JsonProperty("edited")
    EDITED,
    @JsonProperty("closed")
    CLOSED,
    @JsonProperty("assigned")
    ASSIGNED,
    @JsonProperty("unassigned")
    UNASSIGNED,
    @JsonProperty("review_requested")
    REVIEW_REQUESTED,
    @JsonProperty("review_request_removed")
    REVIEW_REQUEST_REMOVED,
    @JsonProperty("ready_for_review")
    READY_FOR_REVIEW,
    @JsonProperty("converted_to_draft")
    CONVERTED_TO_DRAFT,
    @JsonProperty("labeled")
    LABELED,
    @JsonProperty("unlabeled")
    UNLABELED,
    @JsonProperty("synchronize")
    SYNCHRONIZE,
    @JsonProperty("auto_merge_enabled")
    AUTO_MERGE_ENABLED,
    @JsonProperty("auto_merge_disabled")
    AUTO_MERGE_DISABLED,
    @JsonProperty("locked")
    LOCKED,
    @JsonProperty("unlocked")
    UNLOCKED,
    @JsonProperty("reopened")
    REOPENED;
  }
}
