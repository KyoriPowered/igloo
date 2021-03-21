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
package net.kyori.github.util.http;

import com.google.api.client.http.HttpResponse;
import com.google.common.reflect.TypeToken;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class ResponseImpl implements Response {
  private final RequestImpl request;
  private final HttpResponse response;

  ResponseImpl(final RequestImpl request, final HttpResponse response) {
    this.request = request;
    this.response = response;
  }

  @Override
  public <R> R as(final TypeToken<R> type) throws IOException {
    try {
      return this.request.json.readValue(this.response.parseAsString(), this.request.json.getTypeFactory().constructType(type.getType()));
    } finally {
      this.close();
    }
  }

  @Override
  public @NonNull Link link() {
    final String header = this.response.getHeaders().getFirstHeaderStringValue("Link");
    if(header == null) {
      return EmptyLink.INSTANCE;
    }
    return new LinkImpl(this.request, header);
  }

  @Override
  public void close() throws IOException {
    this.response.disconnect();
  }
}
