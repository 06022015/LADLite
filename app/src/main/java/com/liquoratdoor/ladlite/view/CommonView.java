package com.liquoratdoor.ladlite.view;

import com.liquoratdoor.ladlite.dto.StatusDTO;

/**
 * Created by ashqures on 10/21/16.
 */
public interface CommonView<T> extends LoadDataView {

    void renderView(T obj);

    void status(StatusDTO statusDTO);



}
