package com.liquoratdoor.ladlite.view;


import com.liquoratdoor.ladlite.dto.StatusDTO;
import com.liquoratdoor.ladlite.dto.UserDTO;

/**
 * Created by ashqures on 8/19/16.
 */
public interface AuthView extends LoadDataView {

    void handleSignIn(UserDTO userDTO);

    void handleIdentifyUser(StatusDTO statusDTO);

}
