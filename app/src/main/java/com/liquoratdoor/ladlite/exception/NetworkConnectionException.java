/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liquoratdoor.ladlite.exception;

import java.util.List;

/**
 * Exception throw by the application when a there is a network connection exception.
 */
public class NetworkConnectionException extends Throwable implements ErrorBundle{

  private final String message;

  public NetworkConnectionException() {
    super();
    this.message = "No Internet Connection";
  }

  public NetworkConnectionException(final String message) {
    super(message);
    this.message = message;
  }

  public NetworkConnectionException(final String message, final Throwable cause) {
    super(message, cause);
    this.message = message;
  }

  public NetworkConnectionException(final Throwable cause) {
    super(cause);
    this.message = cause.getMessage();
  }

  @Override
  public Integer getCode() {
    return 503;
  }

  @Override
  public Throwable getException() {
   return this;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public List<String> getErrors() {
    return null;
  }
}
