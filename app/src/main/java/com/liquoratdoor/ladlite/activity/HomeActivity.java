package com.liquoratdoor.ladlite.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.liquoratdoor.ladlite.adaptar.OrderAdapter;
import com.liquoratdoor.ladlite.dto.OrderDTO;
import com.liquoratdoor.ladlite.presenter.OrderPresenter;
import com.liquoratdoor.ladlite.view.OrderView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ashqures on 10/19/16.
 */
public class HomeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor>, OrderView {

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private OrderPresenter presenter;

    private OrderAdapter orderAdapter;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.order_list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.home_layout);
        setupToolbar(false);
        FrameLayout container = (FrameLayout) findViewById(R.id.fragmentContainer);
        if(null!=container){
            View inflatedLayout= getLayoutInflater().inflate(R.layout.fragment_order, null, false);
            container.addView(inflatedLayout);
        }
        ButterKnife.bind(this);
        this.presenter = new OrderPresenter();
    }

    @Override
    public void onPostCreate( Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        this.presenter.setView(this);
        this.swipeRefreshLayout.setOnRefreshListener(this);
        this.swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadOrder();
                                    }
                                }
        );
    }


    private void loadOrder(){
        setUpView();
        this.swipeRefreshLayout.setRefreshing(true);
        this.presenter.initialize();
    }

    private void setUpView(){
        this.orderAdapter = new OrderAdapter(context(), R.layout.order_list_view_item);
        this.listView.setAdapter(orderAdapter);
        this.orderAdapter.setOnItemClickListener(onOrderClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void handleOrders(List<OrderDTO> orderDTOs) {
        this.listView.invalidate();
        this.orderAdapter.setItemCollection(orderDTOs);
        this.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void handleOrder(OrderDTO orderDTO) {
        this.navigator.navigateToOrderDetail(context(), orderDTO.getId());
        //showToastMessage(orderDTO.getOrderNumber());
    }

    private OrderAdapter.OnOrderClickListener onOrderClickListener =
            new OrderAdapter.OnOrderClickListener() {
                @Override
                public void onOrderClicked(OrderDTO orderDTO) {
                    if (null != HomeActivity.this.presenter && null != orderDTO) {
                        HomeActivity.this.presenter.onOrderClicked(orderDTO);
                    }
                }
            };

    @Override
    public void onRefresh() {
        loadOrder();
    }
}
