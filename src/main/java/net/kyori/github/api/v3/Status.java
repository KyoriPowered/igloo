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

/**
 * A status.
 *
 * @since 2.0.0
 */
public interface Status {
  /**
   * Gets the state.
   *
   * @return the state
   * @since 2.0.0
   */
  @NonNull State state();

  /**
   * Gets the target url.
   *
   * @return the target url
   * @since 2.0.0
   */
  String target_url();

  /**
   * Gets the description.
   *
   * @return the description
   * @since 2.0.0
   */
  String description();

  /**
   * Gets the context.
   *
   * @return the context
   * @since 2.0.0
   */
  String context();

  /**
   * Inheritance hack.
   *
   * @since 2.0.0
   */
  interface AbstractCreate extends StatusPartial {
  }

  /**
   * A document that can be submitted during status creation.
   *
   * @since 2.0.0
   */
  interface Create extends AbstractCreate, StatusPartial.StatePartial {
    /**
     * A document containing all information that may be submitted during creation.
     *
     * @since 2.0.0
     */
    interface Full extends Create, TargetUrlPartial, DescriptionPartial, ContextPartial {
    }
  }

  /**
   * The state of a status.
   *
   * @since 2.0.0
   */
  enum State {
    @JsonProperty("error")
    ERROR,
    @JsonProperty("failure")
    FAILURE,
    @JsonProperty("pending")
    PENDING,
    @JsonProperty("success")
    SUCCESS;
  }
}
