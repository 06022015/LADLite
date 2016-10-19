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
package com.liquoratdoor.ladlite.interector;

import android.content.Context;
import android.widget.Toast;

import com.liquoratdoor.ladlite.exception.ErrorBundle;


/**
 * Default subscriber base class to be used whenever you want default error handling.
 */
public abstract class DefaultSubscriber<T> extends rx.Subscriber<T> {


  public abstract Context getContext();

  @Override
  public void onCompleted() {
    // no-op by default.
  }

  @Override
  public void onError(Throwable e) {
    // no-op by default.
    Toast.makeText(getContext(), ((ErrorBundle)e).getMessage(), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onNext(T t) {
    // no-op by default.
  }


}
