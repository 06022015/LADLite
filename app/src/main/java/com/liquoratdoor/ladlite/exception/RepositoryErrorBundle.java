/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liquoratdoor.ladlite.exception;

import java.util.List;

/**
 * Wrapper around Exceptions used to manage errors in the repository.
 */
public class RepositoryErrorBundle extends Throwable implements ErrorBundle {

    private final Exception exception;
    private final Integer code;

    public RepositoryErrorBundle(Exception exception) {
        this.exception = exception;
        this.code = 404;
    }

    public RepositoryErrorBundle(Exception exception, int code) {
        this.exception = exception;
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public Throwable getException() {
        return this;
    }

    @Override
    public String getMessage() {
        String message = "";
        if (this.exception != null) {
            message = this.exception.getMessage();
        }
        return message;
    }

    @Override
    public List<String> getErrors() {
        return null;
    }
}
