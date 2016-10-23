package com.liquoratdoor.ladlite.dto;


import com.liquoratdoor.ladlite.util.ValidatorUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ashqures on 10/19/16.
 */
public class DataMapper {

    //{"id":3,"user":{"id":1,"mobile":null,"name":"Ashif Qureshi","email":"ashifqureshi15@gmail.com","password":null,"confirmPassword":null,"numberVerified":false,"emailVerified":false},"token":"c912edf350772cb3b24d8ef73ef99bad539af88fcc6081df93b6fde03900450af69a9449d846b1b081d43c84212270d10654aa8f382eca146f3bc4ccd0f75f9c"}



    public DataMapper() {
    }

    public UserDTO transformUser(JSONObject json) throws JSONException {
        UserDTO userDTO = new UserDTO();
        JSONObject user = json.getJSONObject("user");
        userDTO.setId(user.getLong("id"));
        userDTO.setName(user.getString("name"));
        if (user.has("mobile"))
            userDTO.setMobile(user.getString("mobile"));
        if (user.has("email"))
            userDTO.setEmail(user.getString("email"));
        userDTO.setMobileVerified(user.getBoolean("numberVerified"));
        userDTO.setEmailVerified(user.getBoolean("emailVerified"));
        userDTO.setToken(json.getString("token"));
        return userDTO;
    }

    public StatusDTO transformStatus(JSONObject json) throws JSONException {
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setCode(json.getInt("code"));
        statusDTO.setMessage(json.getString("message"));
        return statusDTO;
    }


    public List<OrderDTO> transformOrders(JSONArray jsonArray) throws JSONException {
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            orderDTOs.add(transformOrder(jsonArray.getJSONObject(i)));
        }
        return orderDTOs;
    }


    public OrderDTO transformOrder(JSONObject json) throws JSONException {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(json.getLong("id"));
        orderDTO.setOrderNumber(json.getString("orderNumber"));
        orderDTO.setState(OrderState.valueOf(json.getString("state")));
        orderDTO.setAddressDTO(transformAddress(json.getJSONObject("address")));
        JSONObject customer = json.getJSONObject("customer");
        orderDTO.setCustomerName(customer.getString("name"));
        if(ValidatorUtil.isNotEmpty(orderDTO.getAddressDTO().getMobile()))
            orderDTO.setCustomerMobile(orderDTO.getAddressDTO().getMobile());
        else
            orderDTO.setCustomerMobile(customer.getString("mobile"));
        if(json.has("order_placed_at") && !json.isNull("order_placed_at"))
            orderDTO.setOrderPlacedTime(new Date(json.getLong("order_placed_at")));
        JSONArray items = json.getJSONArray("orderItem");
        for(int i=0;i<items.length();i++){
            orderDTO.addOrderItem(transferOrderItem(items.getJSONObject(i)));
        }
        return orderDTO;
    }


    public AddressDTO transformAddress(JSONObject json) throws JSONException {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(json.getLong("id"));
        addressDTO.setAddress(json.getString("address"));
        addressDTO.setStreet(json.getString("street"));
        addressDTO.setCity(json.getString("city"));
        addressDTO.setState(json.getString("state"));
        addressDTO.setCountry(json.getString("country"));
        addressDTO.setZipcode(json.getString("zipCode"));
        //addressDTO.setLandMark(json.getString("landmark"));
        addressDTO.setLatitude(json.getString("latitude"));
        addressDTO.setLongitude(json.getString("longitude"));
        if(json.has(json.getString("mobileNumber")) && ValidatorUtil.isNotEmpty(json.getString("mobileNumber")))
            addressDTO.setMobile(json.getString("mobileNumber"));
        return addressDTO;
    }

    public OrderItemDTO transferOrderItem(JSONObject json) throws JSONException {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        JSONObject item = json.getJSONObject("item");
        orderItemDTO.setId(json.getLong("id"));
        orderItemDTO.setName(item.getString("name"));
        if(json.has("price") && null != json.get("price"))
            //orderItemDTO.setPrice(json.getDouble("299.50"));
            orderItemDTO.setPrice(299.80);
        orderItemDTO.setSize(json.getString("itemSize"));
        orderItemDTO.setQuantity(json.getInt("quantity"));
        if(item.has("imageURL") && item.getJSONArray("imageURL").length()>0){
            orderItemDTO.setImageURL(item.getJSONArray("imageURL").getString(0));
        }
        orderItemDTO.setDescription(item.getString("description"));
        orderItemDTO.setBrandName(item.getJSONObject("brand").getString("name"));
        return orderItemDTO;
    }


    /*{
        "count": 1,
            "summary": null,
            "items": [
        {
            "id": 1,
                "orderNumber": "4IT0PIFZ",
                "customer": {
            "id": 1,
                    "username": "ashifqureshi15@gmail.com",
                    "name": "Ashif Qureshi",
                    "email": "ashifqureshi15@gmail.com",
                    "mobile": "8050283640",
                    "numberVerified": true,
                    "emailVerified": true,
                    "dob": "15-12-1987",
                    "accountNonExpired": true,
                    "accountNonLocked": true,
                    "credentialsNonExpired": true,
                    "enabled": true
        },
            "totalAmount": 336,
                "orderFrom": "WEB",
                "address": {
            "id": 1,
                    "address": "4th B cross, 9th Main",
                    "street": "New Thippasandra",
                    "city": "Bangalore",
                    "state": "Karnataka",
                    "country": "India",
                    "zipCode": "560075",
                    "mobileNumber": null,
                    "alternateNumber": null,
                    "latitude": null,
                    "longitude": null,
                    "addressCompleted": true
        },
            "orderItem": [
            {
                "id": 1,
                    "item": {
                "id": 2,
                        "name": "Beer",
                        "description": "http://10.0.2.2:8080/hangover/images/product/Antica-Formula-Carpano-min.png",
                        "brand": {
                    "id": 1,
                            "code": "kf22",
                            "name": "Kingfisher",
                            "logo": "http://localhost:8080/hangover/images/logo/partner1.jpg",
                            "displayName": "Kingfisher",
                            "description": "Alcohol",
                            "url": null,
                            "contactPerson": null,
                            "contactNumber": null
                },
                "count": 10,
                        "itemDetailList": [
                {
                    "id": 3,
                        "itemSize": "300",
                        "quantity": "15",
                        "modelNumber": "AXYZ",
                        "sellingPrice": 79
                },
                {
                    "id": 4,
                        "itemSize": "600",
                        "quantity": "10",
                        "modelNumber": "AXYZ",
                        "sellingPrice": 179
                }
                ],
                "status": "AVAILABLE",
                        "imageURL": [
                "http://localhost:8080/hangover/images/product/Antica-Formula-Carpano-min.png"
                ]
            },
                "itemSize": "300",
                    "price": null,
                    "quantity": 3,
                    "orderItemState": "ORDERED",
                    "deliverAt": null
            },
            {
                "id": 2,
                    "item": {
                "id": 5,
                        "name": "Wine",
                        "description": "http://10.0.2.2:8080/hangover/images/product/Antica-Formula-Carpano-min.png",
                        "brand": {
                    "id": 2,
                            "code": "drght",
                            "name": "RedLabel",
                            "logo": "http://localhost:8080/hangover/images/logo/partner2.jpg",
                            "displayName": "RedLabel",
                            "description": "Wishky",
                            "url": null,
                            "contactPerson": null,
                            "contactNumber": null
                },
                "count": 10,
                        "itemDetailList": [
                {
                    "id": 9,
                        "itemSize": "300",
                        "quantity": "40",
                        "modelNumber": "AXYZ",
                        "sellingPrice": 99
                },
                {
                    "id": 10,
                        "itemSize": "600",
                        "quantity": "30",
                        "modelNumber": "AXYZ",
                        "sellingPrice": 199
                }
                ],
                "status": "AVAILABLE",
                        "imageURL": [
                "http://localhost:8080/hangover/images/product/Antica-Formula-Carpano-min.png"
                ]
            },
                "itemSize": "300",
                    "price": null,
                    "quantity": 1,
                    "orderItemState": "ORDERED",
                    "deliverAt": null
            }
            ],
            "state": "PAYMENT_SUCCESS"
        }
        ],
        "status": null
    }*/


}
