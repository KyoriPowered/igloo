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

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Partial documents used during status creation.
 *
 * @since 2.0.0
 */
public interface StatusPartial {
  /**
   * A document representing a status's state.
   *
   * @since 2.0.0
   */
  interface StatePartial extends Status.AbstractCreate {
    /**
     * Gets the status's state.
     *
     * @return the status state
     * @since 2.0.0
     */
    Status.@Nullable State state();
  }

  /**
   * A document representing an status's target url.
   *
   * @since 2.0.0
   */
  interface TargetUrlPartial extends Status.Create {
    /**
     * Gets the status's target url.
     *
     * @return the status target url
     * @since 2.0.0
     */
    @Nullable String target_url();
  }

  /**
   * A document representing an status's description.
   *
   * @since 2.0.0
   */
  interface DescriptionPartial extends Status.Create {
    /**
     * Gets the status's description.
     *
     * @return the status description
     * @since 2.0.0
     */
    @Nullable String description();
  }

  /**
   * A document representing an issue's context.
   *
   * @since 2.0.0
   */
  interface ContextPartial extends Status.Create {
    /**
     * Gets the status's context.
     *
     * @return the status context
     * @since 2.0.0
     */
    @Nullable String context();
  }
}
