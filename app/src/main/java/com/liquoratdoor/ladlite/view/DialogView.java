package com.liquoratdoor.ladlite.view;


import com.liquoratdoor.ladlite.dto.StatusDTO;

/**
 * Created by ashqures on 9/4/16.
 */
public interface DialogView extends LoadDataView {


    void positiveAction(StatusDTO status);

    void negativeAction(StatusDTO status);

    void onFailure(StatusDTO statusDTO);

}
