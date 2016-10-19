package com.liquoratdoor.ladlite.task;


import com.liquoratdoor.ladlite.exception.ErrorBundle;

/**
 * Created by ashqures on 8/12/16.
 */
public class AsyncTaskResult<T> {

    private T result;
    private ErrorBundle errorBundle;


    public AsyncTaskResult(T result) {
        super();
        this.result = result;
    }

    public AsyncTaskResult(ErrorBundle errorBundle) {
        super();
        this.errorBundle = errorBundle;
    }


    public boolean hasError(){
        return null!= errorBundle;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ErrorBundle getErrorBundle() {
        return errorBundle;
    }

    public void setErrorBundle(ErrorBundle errorBundle) {
        this.errorBundle = errorBundle;
    }
}
