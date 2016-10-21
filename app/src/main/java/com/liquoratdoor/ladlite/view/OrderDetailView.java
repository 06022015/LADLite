package com.liquoratdoor.ladlite.view;

import com.liquoratdoor.ladlite.dto.OrderDTO;

import java.util.List;

/**
 * Created by ashqures on 8/21/16.
 */
public interface OrderDetailView extends LoadDataView {

    void handleOrder(OrderDTO orderDTOs);


}
