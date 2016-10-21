package com.liquoratdoor.ladlite.presenter;

import android.content.Context;

import com.liquoratdoor.ladlite.dto.DataMapper;
import com.liquoratdoor.ladlite.dto.OrderDTO;
import com.liquoratdoor.ladlite.exception.DefaultErrorBundle;
import com.liquoratdoor.ladlite.exception.ErrorBundle;
import com.liquoratdoor.ladlite.exception.ErrorMessageFactory;
import com.liquoratdoor.ladlite.interector.DefaultSubscriber;
import com.liquoratdoor.ladlite.task.AsyncTaskHandler;
import com.liquoratdoor.ladlite.task.CommonTask;
import com.liquoratdoor.ladlite.view.OrderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ashqures on 10/20/16.
 */
public class OrderPresenter implements Presenter {

    private enum SubscriberType{
        ORDERS,  DETAIL
    }

    OrderView view;
    DataMapper dataMapper;
    CommonTask mTask;

    public OrderPresenter() {
        this.dataMapper = new DataMapper();
    }

    public void setView(OrderView authView){
        this.view = authView;
    }

    public void onOrderClicked(OrderDTO orderDTO) {
        this.view.handleOrder(orderDTO);
    }

    public void initialize(){
        this.loadOrder();
    }

    private void loadOrder(){
        this.mTask = AsyncTaskHandler.getOrderTask(new OrderSubscriber());
        this.mTask.execute();
    }


    private void handleOrders(List<OrderDTO> orderDTOs){
        this.view.handleOrders(orderDTOs);
    }


    private void handleOrder(OrderDTO orderDTO){
        this.view.handleOrder(orderDTO);
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if(null!= this.mTask){
            this.mTask.cancel(true);
        }
        this.view=null;
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.view.context(), errorBundle);
        this.view.showError(errorMessage);
    }


    private final class OrderSubscriber extends DefaultSubscriber<JSONObject> {

        @Override
        public Context getContext() {
            return OrderPresenter.this.view.context();
        }

        @Override
        public void onCompleted() {
            OrderPresenter.this.view.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            OrderPresenter.this.view.hideLoading();
            OrderPresenter.this.showErrorMessage((ErrorBundle) e);
            OrderPresenter.this.view.showRetry();
        }

        @Override
        public void onNext(JSONObject response) {
            try{
               OrderPresenter.this.handleOrders(OrderPresenter.this.dataMapper.transformOrders(response.getJSONArray("items")));
            }catch (JSONException je){
                onError(new DefaultErrorBundle(je));
            }
        }
    }
}
