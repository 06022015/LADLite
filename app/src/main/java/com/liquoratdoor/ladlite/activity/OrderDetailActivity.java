package com.liquoratdoor.ladlite.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.liquoratdoor.ladlite.dto.OrderDTO;
import com.liquoratdoor.ladlite.presenter.OrderDetailPresenter;
import com.liquoratdoor.ladlite.presenter.OrderPresenter;
import com.liquoratdoor.ladlite.view.CommonView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ashqures on 10/20/16.
 */
public class OrderDetailActivity extends BaseActivity implements CommonView<OrderDTO> {

    public static final String INTENT_EXTRA_PARAM_ITEM_ID = "org.hangover.INTENT_PARAM_ITEM";
    public static final String INSTANCE_STATE_PARAM_ITEM_ID = "org.hangover.STATE_PARAM_ITEM";

    public static Intent getCallingIntent(Context context, Long orderId) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_ITEM_ID, orderId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private Long orderId;

    private OrderDetailPresenter presenter;

    private OrderDTO orderDTO;

    @Bind(R.id.order_number)
    TextView orderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.home_layout);
        setupToolbar(true);
        if (savedInstanceState == null) {
            this.orderId = getIntent().getLongExtra(OrderDetailActivity.INTENT_EXTRA_PARAM_ITEM_ID, -1);
        } else {
            this.orderId = savedInstanceState.getLong(OrderDetailActivity.INSTANCE_STATE_PARAM_ITEM_ID);
        }
        FrameLayout container = (FrameLayout) findViewById(R.id.fragmentContainer);
        if(null!=container){
            View inflatedLayout= getLayoutInflater().inflate(R.layout.fragment_order_detail, null, false);
            container.addView(inflatedLayout);
        }
        ButterKnife.bind(this);
        this.presenter = new OrderDetailPresenter();
    }


    @Override
    public void onPostCreate( Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        this.presenter.setView(this);
        if(null== savedInstanceState)
            this.loadOrderDetail();
    }


    private void loadOrderDetail(){
        this.presenter.attemptOderDetail(this.orderId);
    }


    private void setUpView(){

    }

    @Override
    public void renderView(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
        renderView();
    }


    private void renderView(){
        this.orderNumber.setText(this.orderDTO.getOrderNumber());
        showToastMessage(this.orderDTO.getOrderNumber());
    }
}
