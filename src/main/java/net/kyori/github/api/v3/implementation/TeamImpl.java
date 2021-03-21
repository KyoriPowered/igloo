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

import com.google.common.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.kyori.github.api.v3.Team;
import net.kyori.github.api.v3.User;
import net.kyori.github.util.http.Request;
import org.checkerframework.checker.nullness.qual.NonNull;

final class TeamImpl implements Team {
  @SuppressWarnings("UnstableApiUsage")
  private static final TypeToken<List<Partial.User>> MEMBERS_TYPE = new TypeToken<List<Partial.User>>() {};
  private final Request request;

  TeamImpl(final Request request, final String slug) {
    this.request = request.path(slug);
  }

  @Override
  public @NonNull List<User> members() throws IOException {
    final List<Partial.User> partials = this.request.path("members").get().as(MEMBERS_TYPE);
    final List<User> members = new ArrayList<>(partials.size());
    for(final Partial.User partial : partials) {
      members.add(new UserImpl(partial.login, partial.name, partial.avatar_url));
    }
    return members;
  }
}
