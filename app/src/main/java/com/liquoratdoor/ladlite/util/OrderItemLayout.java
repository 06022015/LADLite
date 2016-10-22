package com.liquoratdoor.ladlite.util;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liquoratdoor.ladlite.dto.OrderItemDTO;

import java.util.Date;


/**
 * Created by ashqures on 10/22/16.
 */
public class OrderItemLayout extends RelativeLayout {

    private Context context;
    private OrderItemDTO orderItemDTO;

    public OrderItemLayout(Context context, OrderItemDTO orderItemDTO) {
        super(context);
        this.context = context;
        this.orderItemDTO = orderItemDTO;
        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        this.setBackgroundColor(Color.WHITE);
        init();
    }

    public void init() {
        this.addItemName(this.orderItemDTO.getName());
        this.addItemSize(this.orderItemDTO.getSize());
        this.addItemUnitPrice(this.orderItemDTO.getPrice());
        this.addItemQuantity(this.orderItemDTO.getQuantity());
        this.addItemAmount(this.orderItemDTO.getQuantity() * this.orderItemDTO.getPrice());
        this.addBrandName(this.orderItemDTO.getBrandName());
    }

    public void addItemName(String itemName) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        TextView textView = getTextView(params);
        textView.setText(itemName);
        textView.setId(orderItemDTO.getId().intValue()*100);
        addView(textView);
    }

    public void addItemSize(String itemSize) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        TextView textView = getTextView(params);
        textView.setId(orderItemDTO.getId().intValue()*200);
        textView.setText(itemSize);
        this.addView(textView);
    }

    public void addItemUnitPrice(Double itemPrice) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        TextView textView = getTextView(params);
        textView.setId(orderItemDTO.getId().intValue()*300);
        textView.setText(String.format("%.2f", itemPrice));
        this.addView(textView);
    }

    public void addItemQuantity(int quantity) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        TextView textView = getTextView(params);
        textView.setId(orderItemDTO.getId().intValue()*400);
        textView.setText(quantity + "");
        this.addView(textView);
    }

    public void addItemAmount(Double itemAmount) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        TextView textView = getTextView(params);
        textView.setId(orderItemDTO.getId().intValue()*500);
        textView.setText(itemAmount + "");
        this.addView(textView);
    }


    public void addBrandName(String brandName) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        TextView textView = getTextView(params);
        textView.setId(orderItemDTO.getId().intValue()*600);
        textView.setText(brandName);
        this.addView(textView);
    }


    public TextView getTextView(RelativeLayout.LayoutParams params) {
        TextView textView = new TextView(this.context);
        textView.setWidth(LayoutParams.MATCH_PARENT);
        textView.setHeight(CommonUtil.dpToPx(50));
        textView.setPadding(5, 5, 5, 5);
        textView.setLayoutParams(params);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

}
