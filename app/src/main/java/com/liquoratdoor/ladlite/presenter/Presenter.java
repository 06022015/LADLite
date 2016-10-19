package com.liquoratdoor.ladlite.presenter;

/**
 * Created by ashqures on 8/11/16.
 */
public interface Presenter {

    enum Method{
        GET, POST, PUT, DELETE
    }

    void resume();

    void pause();

    void destroy();

}
