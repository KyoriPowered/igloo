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

import java.util.function.Function;
import java.util.function.Supplier;

final class Hacks {
  private Hacks() {
  }

  static <T extends Throwable> RuntimeException yeet(final Throwable object) throws T {
    throw (T) object;
  }

  interface ThrowingFunction<T, R, E extends Throwable> extends Function<T, R> {
    static <T, R, E extends Throwable> ThrowingFunction<T, R, E> of(final ThrowingFunction<T, R, E> tf) {
      return tf;
    }

    @Override
    default R apply(final T t) {
      try {
        return this.apply0(t);
      } catch (final Throwable e) {
        throw yeet(e);
      }
    }

    R apply0(final T t) throws E;
  }

  interface ThrowingSupplier<T, E extends Throwable> extends Supplier<T>, com.google.common.base.Supplier<T> {
    static <T, E extends Throwable> ThrowingSupplier<T, E> of(final ThrowingSupplier<T, E> ts) {
      return ts;
    }

    @Override
    default T get() {
      try {
        return this.get0();
      } catch(final Throwable t) {
        throw yeet(t);
      }
    }

    T get0() throws E;
  }
}
