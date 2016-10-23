package com.liquoratdoor.ladlite.adaptar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.liquoratdoor.ladlite.activity.R;
import com.liquoratdoor.ladlite.dto.OrderDTO;
import com.liquoratdoor.ladlite.dto.OrderState;
import com.liquoratdoor.ladlite.util.CommonUtil;

import java.util.Collection;
import java.util.List;

/**
 * Created by ashqures on 10/20/16.
 */
public class OrderAdapter extends ArrayAdapter<OrderDTO> {

    public interface OnOrderClickListener {
        void onOrderClicked(OrderDTO itemDTO);
    }


    private OnOrderClickListener onOrderClickListener;

    private Context context;

    private int layoutResourceId;

    /*@Inject*/
    public OrderAdapter(Context context, int layoutResourceId) {
        super(context,  layoutResourceId);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public long getItemId(int position) {
        OrderDTO orderDTO = getItem(position);
        return orderDTO.getId();
    }


    public void setItemCollection(List<OrderDTO> orderDTOs) {
        this.validateOrdersCollection(orderDTOs);
        this.addAll(orderDTOs);
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnOrderClickListener onItemClickListener) {
        this.onOrderClickListener = onItemClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new RecordHolder();
            holder.orderNumber = (TextView) row.findViewById(R.id.order_label);
            holder.timeRemain = (TextView) row.findViewById(R.id.time_remain);
            holder.deliveryAddress = (TextView) row.findViewById(R.id.delivery_address);
            holder.orderBrief = (TextView) row.findViewById(R.id.order_brief);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }
        final OrderDTO orderDTO = getItem(position);
        holder.orderNumber.setText(holder.orderNumber.getText().toString().replace("_ORDR_NUMBER", orderDTO.getOrderNumber()));
        holder.deliveryAddress.setText(orderDTO.getAddressDTO().toString());
        String brief = holder.orderBrief.getText().toString();
        brief = brief.replace("_CNT",orderDTO.getOrderItemDTOs().size()+"");
        brief = brief.replace("_QNTY",orderDTO.getTotalQuantity()+"");
        holder.orderBrief.setText(brief);
        if(orderDTO.getState().equals(OrderState.PAYMENT_SUCCESS)){
            holder.orderBrief.setBackgroundColor(Color.GREEN);
        }
        CommonUtil.handleDeliveryTime(holder.timeRemain, orderDTO.getOrderPlacedTime());
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderAdapter.this.onOrderClickListener.onOrderClicked(orderDTO);
            }
        });
        return row;
    }

    private void validateOrdersCollection(Collection<OrderDTO> orderDTOCollection) {
        if (orderDTOCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }


    static class RecordHolder{
        TextView orderNumber;
        TextView timeRemain;
        TextView deliveryAddress;
        TextView orderBrief;
    }
}
