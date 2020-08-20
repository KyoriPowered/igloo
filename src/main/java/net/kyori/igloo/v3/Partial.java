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

import org.checkerframework.checker.nullness.qual.Nullable;

/* package */ interface Partial {
  class Id {
    int id;
  }

  class Issue {
    User user;
    String body;
    int number;
    String html_url;
    @Nullable Object pull_request;
    net.kyori.igloo.v3.Issue.State state;
    String title;
  }

  class Label {
    String url;
    String name;
    String description;
    String color;
  }

  class Permission {
    Collaborator.Permission permission;
  }

  class PullRequest {
    String html_url;
    net.kyori.igloo.v3.PullRequest.State state;
    String title;
    String body;
    User user;
    boolean merged;
  }

  class PullRequestReview {
    User user;
    net.kyori.igloo.v3.PullRequestReview.State state;
    String body;
    String commit_id;
  }

  class Status {
    net.kyori.igloo.v3.Status.State state;
    String target_url;
    String description;
    String context;
  }

  class User {
    String login;
    String name;
    String avatar_url;
  }
}
