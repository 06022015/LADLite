package com.liquoratdoor.ladlite.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liquoratdoor.ladlite.dto.OrderDTO;
import com.liquoratdoor.ladlite.dto.OrderItemDTO;
import com.liquoratdoor.ladlite.dto.OrderState;
import com.liquoratdoor.ladlite.dto.StatusDTO;
import com.liquoratdoor.ladlite.presenter.OrderDetailPresenter;
import com.liquoratdoor.ladlite.service.RestApi;
import com.liquoratdoor.ladlite.util.CommonUtil;
import com.liquoratdoor.ladlite.view.CommonView;

import java.util.HashMap;
import java.util.Map;

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

    @Bind(R.id.order_items)
    LinearLayout orderItemsLayout;

    @Bind(R.id.order_number)
    TextView orderNumber;

    @Bind(R.id.delivery_time_left)
    TextView deliveryTimeLeft;

    @Bind(R.id.delivery_address)
    TextView deliveryAddress;


    @Bind(R.id.total_item)
    TextView totalItem;

    @Bind(R.id.total_quantity)
    TextView totalQuantity;

    @Bind(R.id.customer_name)
    TextView customerName;

    @Bind(R.id.customer_number)
    TextView customerNumber;


    @Bind(R.id.button_order_accept)
    TextView buttonPstveAction;

    @Bind(R.id.button_order_reject)
    TextView buttonNgtveAction;

    private OrderState pstveProcessState;
    private OrderState ngtveProcessState;
    private OrderState currentState;

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
        if (null != container) {
            View inflatedLayout = getLayoutInflater().inflate(R.layout.fragment_order_detail, null, false);
            container.addView(inflatedLayout);
        }
        ButterKnife.bind(this);
        this.presenter = new OrderDetailPresenter();
    }


    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.presenter.setView(this);
        if (null == savedInstanceState)
            this.loadOrderDetail();
    }


    private void loadOrderDetail() {
        this.presenter.attemptOderDetail(this.orderId);
    }


    private void setUpView() {

    }

    @Override
    public void renderView(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
        renderView();
    }

    @Override
    public void status(StatusDTO statusDTO) {
        this.orderDTO.setState(this.currentState);
        setUpAction();
    }


    private void renderView() {
        String orderNumber = this.orderNumber.getText().toString().replace("_ORDRNUM", this.orderDTO.getOrderNumber());
        this.orderNumber.setText(orderNumber);
        //showToastMessage(this.orderDTO.getOrderNumber());
        CommonUtil.handleDeliveryTime(this.deliveryTimeLeft, this.orderDTO.getOrderPlacedTime());
        this.deliveryAddress.setText(this.orderDTO.getAddressDTO().getCompleteAddress());
        String ttlItem = this.totalItem.getText().toString().replace("_ITEM", this.orderDTO.getOrderItemDTOs().size() + "");
        this.totalItem.setText(ttlItem);
        String ttlQnty = this.totalQuantity.getText().toString().replace("_QNTY", orderDTO.getTotalQuantity() + "");
        this.totalQuantity.setText(ttlQnty);
        String name = this.customerName.getText().toString().replace("_NAME", orderDTO.getCustomerName());
        String mobile = this.customerNumber.getText().toString().replace("_MOB", orderDTO.getCustomerMobile());
        this.customerName.setText(name);
        this.customerNumber.setText(mobile);
        CommonUtil.attachPhoneListener(this);
        this.customerNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + OrderDetailActivity.this.orderDTO.getCustomerMobile()));
                checkSelfPermission(Manifest.permission.CALL_PHONE, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                startActivity(callIntent);
            }
        });
        for(OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOs()){
            this.orderItemsLayout.addView(getOrderItemRow(orderItemDTO));
        }
        setUpAction();
    }

    private void setUpAction(){
        this.buttonPstveAction.setText(OrderState.get(orderDTO.getState()));
        this.buttonNgtveAction.setText(OrderState.getNgtvState(orderDTO.getState()));
        switch (OrderDetailActivity.this.orderDTO.getState()){
            case PAYMENT_SUCCESS:
                this.pstveProcessState = OrderState.ORDER_ACCEPTED;
                this.ngtveProcessState = OrderState.ORDER_REJECTED;
                break;
            case ORDER_ACCEPTED:
                this.pstveProcessState=OrderState.ORDER_INVOICE_GENERATED;
                break;
            case ORDER_INVOICE_GENERATED:this.pstveProcessState=OrderState.ORDER_PACKED;
                break;
            case ORDER_PACKED:this.pstveProcessState = OrderState.ORDER_DISPATCHED;
                break;
            case ORDER_DISPATCHED:
                this.pstveProcessState = OrderState.ORDER_DELIVERED;
                this.ngtveProcessState = OrderState.ORDER_DELIVERY_FAILED;
                break;
            default:
                this.buttonPstveAction.setEnabled(false);
                this.buttonNgtveAction.setEnabled(false);
                break;
        }
        if(!orderDTO.getState().equals(OrderState.PAYMENT_SUCCESS) && !this.orderDTO.getState().equals(OrderState.ORDER_DISPATCHED)){
            this.buttonNgtveAction.setVisibility(View.INVISIBLE);
        }else{
            this.buttonNgtveAction.setVisibility(View.VISIBLE);
        }
        if(orderDTO.getState().equals(OrderState.ORDER_REJECTED)){
            this.buttonPstveAction.setVisibility(View.INVISIBLE);
        }
        this.buttonPstveAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetailActivity.this.processOrderState(OrderDetailActivity.this.pstveProcessState);
            }
        });
        this.buttonNgtveAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetailActivity.this.processOrderState(OrderDetailActivity.this.ngtveProcessState);
            }
        });
    }

    private void processOrderState(OrderState state){
        this.currentState = state;
        Map<String,String> param = new HashMap<>();
        param.put(RestApi.ORDER_ID, orderDTO.getId()+"");
        param.put(RestApi.ORDER_SATE, state.name());
        this.presenter.processOrderState(param);
    }

    public View getOrderItemRow(OrderItemDTO orderItemDTO){
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.order_item_view, this.orderItemsLayout, false);
        TextView itemName = (TextView) row.findViewById(R.id.item_name);
        itemName.setText(orderItemDTO.getName());
        TextView brandName = (TextView) row.findViewById(R.id.brand_name);
        brandName.setText(orderItemDTO.getBrandName());
        TextView itemSize = (TextView) row.findViewById(R.id.item_size);
        itemSize.setText(orderItemDTO.getSize());
        TextView unitPrice = (TextView) row.findViewById(R.id.item_unit_price);
        unitPrice.setText(String.format("%.2f", orderItemDTO.getPrice()));
        TextView quantity = (TextView) row.findViewById(R.id.item_quantity);
        quantity.setText(orderItemDTO.getQuantity()+"");
        TextView itemAmount = (TextView) row.findViewById(R.id.item_amount);
        itemAmount.setText(String.format("%.2f", orderItemDTO.getQuantity()*orderItemDTO.getPrice()));
        return row;
    }

}
