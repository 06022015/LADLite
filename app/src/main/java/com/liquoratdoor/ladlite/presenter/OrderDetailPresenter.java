package com.liquoratdoor.ladlite.presenter;

import android.content.Context;

import com.liquoratdoor.ladlite.dto.DataMapper;
import com.liquoratdoor.ladlite.dto.OrderDTO;
import com.liquoratdoor.ladlite.dto.StatusDTO;
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

import java.util.Map;

/**
 * Created by ashqures on 10/21/16.
 */
public class OrderDetailPresenter implements Presenter {

    private enum SubscriberType{
        DETAIL, PROCESS
    }

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
        this.mTask = AsyncTaskHandler.getOrderDetailTask(new OrderDetailSubscriber(SubscriberType.DETAIL), orderId);
        this.mTask.execute();
    }


    public void processOrderState(Map<String,String> param){
        this.mTask = AsyncTaskHandler.getOrderProcessTask(new OrderDetailSubscriber(SubscriberType.PROCESS));
        this.mTask.execute(param);
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


    private void handleOrderProcessing(StatusDTO status){
        this.view.status(status);
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.view.context(), errorBundle);
        this.view.showError(errorMessage);
    }

    private final class OrderDetailSubscriber extends DefaultSubscriber<JSONObject> {

        private SubscriberType type;

        public OrderDetailSubscriber(SubscriberType type){
            this.type = type;
        }

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
                switch (type){
                    case DETAIL:
                        OrderDetailPresenter.this.handleOrder(OrderDetailPresenter.this.dataMapper.transformOrder(response));
                        break;
                    case PROCESS:
                        OrderDetailPresenter.this.handleOrderProcessing(OrderDetailPresenter.this.dataMapper.transformStatus(response));
                        break;
                    default:break;
                }

            }catch (JSONException je){
                onError(new DefaultErrorBundle(je));
            }
        }
    }

}
