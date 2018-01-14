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
import net.kyori.blizzard.NonNull;

import java.io.IOException;

/**
 * A repository collaborator.
 */
public interface Collaborator {
  /**
   * Gets the permission.
   *
   * @return the permission
   * @throws IOException if an exception occurs while getting the permission
   */
  @NonNull
  Permission permission() throws IOException;

  /**
   * The permission levels that are possible for a collaborator to have.
   */
  enum Permission {
    @SerializedName("admin")
    ADMIN,
    @SerializedName("write")
    WRITE,
    @SerializedName("read")
    READ,
    @SerializedName("none")
    NONE;

    /**
     * Tests if the permission has write access.
     *
     * @return {@code true} if the permission has write access
     */
    public boolean write() {
      return this == ADMIN || this == WRITE;
    }
  }

  final class Impl implements Collaborator {
    private final Request request;

    Impl(final Request request, final User user) {
      this.request = request.path(user.login());
    }

    @NonNull
    @Override
    public Permission permission() throws IOException {
      return this.request.path("permission").get(Partial.Permission.class).permission;
    }
  }
}
