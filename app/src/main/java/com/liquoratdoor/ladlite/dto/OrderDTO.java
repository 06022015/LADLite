package com.liquoratdoor.ladlite.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ashqures on 10/20/16.
 */
public class OrderDTO extends BaseDTO {


    private String orderNumber;
    private Date orderPlacedTime = new Date();
    private AddressDTO addressDTO;
    private List<OrderItemDTO> orderItemDTOs;
    private OrderState state;
    private String customerName;
    private String customerMobile;


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderPlacedTime() {
        return orderPlacedTime;
    }

    public void setOrderPlacedTime(Date orderPlacedTime) {
        this.orderPlacedTime = orderPlacedTime;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public List<OrderItemDTO> getOrderItemDTOs() {
        return orderItemDTOs;
    }

    public void setOrderItemDTOs(List<OrderItemDTO> orderItemDTOs) {
        this.orderItemDTOs = orderItemDTOs;
    }

    public void addOrderItem(OrderItemDTO orderItemDTO){
        if(null == getOrderItemDTOs())
            setOrderItemDTOs(new ArrayList<OrderItemDTO>());
        getOrderItemDTOs().add(orderItemDTO);
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public int getTotalQuantity(){
        int quantity = 0;
        for(OrderItemDTO orderItemDTO : getOrderItemDTOs()){
            quantity = quantity+orderItemDTO.getQuantity();
        }
        return quantity;
    }
}
