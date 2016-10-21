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
import com.liquoratdoor.ladlite.view.CommonView;
import com.liquoratdoor.ladlite.view.OrderView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ashqures on 10/21/16.
 */
public class OrderDetailPresenter implements Presenter {


    private DataMapper dataMapper;
    private CommonView<OrderDTO> view;
    private CommonTask mTask;

    public OrderDetailPresenter() {
        this.dataMapper = new DataMapper();
    }

    public void setView(CommonView<OrderDTO> view){
        this.view = view;
    }

    public void attemptOderDetail(Long orderId){
        this.mTask = AsyncTaskHandler.getOrderDetailTask(new OrderDetailSubscriber(), orderId);
        this.mTask.execute();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    private void handleOrder(OrderDTO orderDTO){
        this.view.renderView(orderDTO);
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.view.context(), errorBundle);
        this.view.showError(errorMessage);
    }

    private final class OrderDetailSubscriber extends DefaultSubscriber<JSONObject> {

        @Override
        public Context getContext() {
            return OrderDetailPresenter.this.view.context();
        }

        @Override
        public void onCompleted() {
            OrderDetailPresenter.this.view.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            OrderDetailPresenter.this.view.hideLoading();
            OrderDetailPresenter.this.showErrorMessage((ErrorBundle) e);
            OrderDetailPresenter.this.view.showRetry();
        }

        @Override
        public void onNext(JSONObject response) {
            try{
                OrderDetailPresenter.this.handleOrder(OrderDetailPresenter.this.dataMapper.transformOrder(response));
            }catch (JSONException je){
                onError(new DefaultErrorBundle(je));
            }
        }
    }

}
