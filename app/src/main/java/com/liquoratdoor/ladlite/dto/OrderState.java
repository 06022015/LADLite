package com.liquoratdoor.ladlite.dto;

/**
 * Created by ashqures on 10/20/16.
 */
public enum OrderState {

    PAYMENT_SUCCESS,
    ORDER_CANCELED,
    ORDER_ACCEPTED,
    ORDER_REJECTED,
    ORDER_IN_PROCESS,
    ORDER_INVOICE_GENERATED,
    ORDER_PACKED,
    ORDER_DISPATCHED,
    ORDER_DELIVERED,
    ORDER_DELIVERY_FAILED;

    public static String get(OrderState state){
        String stateValue = "";
        switch (state){
            case PAYMENT_SUCCESS:
                stateValue = "Accept";
                break;
            case ORDER_CANCELED:
                stateValue = "";
                break;
            case ORDER_ACCEPTED:
                stateValue = "Invoice";
                break;
            case ORDER_REJECTED:
                stateValue = "Rejected";
                break;
            case ORDER_IN_PROCESS:
                stateValue = "";
                break;
            case ORDER_INVOICE_GENERATED:
                stateValue = "Packed";
                break;
            case ORDER_PACKED:
                stateValue = "Dispatch";
                break;
            case ORDER_DISPATCHED:
                stateValue = "Deliver";
                break;
            case ORDER_DELIVERED:
                stateValue = "Closed";
                break;
            case ORDER_DELIVERY_FAILED:
                stateValue = "Closed";
                break;
            default:
                stateValue = "";
                break;
        }
        return stateValue;
    }

    public static String getNgtvState(OrderState state){
        String stateValue = "";
        switch (state){
            case PAYMENT_SUCCESS:
                stateValue = "Reject";
                break;
            case ORDER_DISPATCHED:
                stateValue = "Failed";
                break;
        }
        return stateValue;
    }

}



