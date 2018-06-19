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

import net.kyori.igloo.http.Request;
import net.kyori.igloo.http.Response;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* package */ final class Paginated<T> implements Iterable<T> {
  private final Function<Request, Response> requestFunction;
  private final Function<Response, Stream<T>> responseFunction;
  private final Pager pager = new Pager();

  /* package */ Paginated(final Request request, final Function<Request, Response> requestFunction, final Function<Response, Stream<T>> responseFunction) {
    this.pager.next = request;
    this.requestFunction = requestFunction;
    this.responseFunction = responseFunction;
  }

  @Override
  public @NonNull Iterator<T> iterator() {
    return this.pager;
  }

  /* package */ class Pager implements Iterator<T> {
    private LinkedList<T> current;
    /* package */ Request next;

    private LinkedList<T> current() {
      if(this.current == null) {
        if(this.next != null) {
          final Response response = Paginated.this.requestFunction.apply(this.next);
          this.next = response.link().next().orElse(null);
          this.current = Paginated.this.responseFunction.apply(response).collect(Collectors.toCollection(LinkedList::new));
        } else {
          throw new NoSuchElementException();
        }
      }
      return this.current;
    }

    @Override
    public boolean hasNext() {
      if(!this.current().isEmpty()) {
        return true;
      } else if(this.next != null) {
        this.current = null;
        return !this.current().isEmpty();
      } else {
        return false;
      }
    }

    @Override
    public T next() {
      return this.current().removeFirst();
    }
  }
}
