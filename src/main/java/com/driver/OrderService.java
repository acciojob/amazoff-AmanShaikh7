package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public void addPartner(String partnerID) {
        orderRepository.addPartner(partnerID);
    }


    public boolean addOrderPartnerPair(String orderId, String partnerId) {
        boolean ans = orderRepository.addOrderPartnerPair(orderId,partnerId);
        if(ans) return true;
        return false;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        List<String> orders = new ArrayList<>();
        return orderRepository.getAllOrders(orders);
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String[] divide = time.split(":");
        int hours = Integer.parseInt(divide[0]);
        int minutes = Integer.parseInt(divide[1]);
        int timeinMinutes= hours*60 + minutes; // delivery time in minutes
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(timeinMinutes,partnerId);

    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int time =  orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        // now convert this time into HH:MM
        String HH = String.valueOf(time/60);
        String MM = String.valueOf(time%60);
        if(HH.length()<2){
            HH = '0'+HH;
        }
        if(MM.length() <2){
            MM = '0'+MM;
        }
        return HH+':'+MM;
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
