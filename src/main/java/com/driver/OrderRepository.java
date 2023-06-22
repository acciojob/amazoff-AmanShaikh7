package com.driver;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    Map<String,Order> OrderDb = new HashMap<>(); // string id will be its primary key
    Map<String,DeliveryPartner> PartnerDB = new HashMap<>();
    Map<String,List<String>> assignedDb = new HashMap<>(); // this stores the data of order assigned to which partner //paertner id -> list of orders

    public void addOrder(Order order) {
        OrderDb.put(order.getId(),order);
    }

    public Order getOrderById(String orderId) {
        //System.out.print(orderId+" "+"in repo");
        return OrderDb.get(orderId);
    }

    public void addPartner(String partnerID) {
            DeliveryPartner partner = new DeliveryPartner(partnerID);
            PartnerDB.put(partnerID,partner);
    }

    public boolean addOrderPartnerPair(String orderId, String partnerId) {
        if(OrderDb.containsKey(orderId) && PartnerDB.containsKey(partnerId)) {
           // assignedDb.put(partnerId,orderId);
            List<String> orders = assignedDb.getOrDefault(partnerId,new ArrayList<>());
            orders.add(orderId);
            assignedDb.put(partnerId,orders);
            DeliveryPartner partner =  PartnerDB.get(partnerId);
            partner.setNumberOfOrders(partner.getNumberOfOrders()+1);
            return true;
        }
        return false;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return PartnerDB.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        DeliveryPartner partn =PartnerDB.get(partnerId);
        return partn.getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
       return assignedDb.get(partnerId);
    }

    public List<String> getAllOrders(List<String> orders) {
        for(String keys : OrderDb.keySet()){
            orders.add(keys);
        }
        return orders;
    }

    public Integer getCountOfUnassignedOrders() {
        Integer count =0;
        for(String orders : OrderDb.keySet()){
            for(String dp : assignedDb.keySet()){
                if(!assignedDb.get(dp).contains(orders)) count++;
            }
        }
        return count;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int timeinMinutes, String partnerId) {
        List<String> orders = assignedDb.get(partnerId);
        Integer count =0;
        for(String id : orders){
            Order o = OrderDb.get(id);
            if(timeinMinutes < o.getDeliveryTime()){
                count++;
            }
            timeinMinutes-=o.getDeliveryTime();
        }
        return count;
    }
}
