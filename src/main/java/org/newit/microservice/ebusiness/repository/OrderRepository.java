package org.newit.microservice.ebusiness.repository;

import java.util.List;

import org.newit.microservice.ebusiness.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class OrderRepository {

    @Autowired
    private RestTemplate restTemplate;

    private static String ORDER_PREFIX = "http://order-service/";
    public Order getOrderById(Long orderId) {
        return restTemplate.getForObject(ORDER_PREFIX + "order/" + orderId, Order.class);
    }

    public void insert(Order order) {
        restTemplate.postForObject(ORDER_PREFIX +"19610/add/order",order,String.class);
    }

    public List<Order> getOrderListByBuyerId(long buyerId) {
        ResponseEntity<List<Order>>
                responseEntity = restTemplate.exchange(ORDER_PREFIX +"order/buyerList?buyerId=" + buyerId, HttpMethod.GET, null,
                                                       new ParameterizedTypeReference<List<Order>>() {});
        List<Order> orderList = responseEntity.getBody();
        return orderList;
    }

    public List<Order> getOrderListBySellerId(long sellerId) {
        ResponseEntity<List<Order>> responseEntity = restTemplate.exchange("http://localhost:19610/order/sellerList?sellerId=" + sellerId, HttpMethod.GET, null,
                                                                           new ParameterizedTypeReference<List<Order>>() {});
        List<Order> orderList = responseEntity.getBody();
//        JSONArray result  =   restTemplate.getForObject("http://localhost:19610/order/sellerList?sellerId=" + sellerId, JSONArray.class);
//        List<Order> orderList = Lists.newArrayList();
//        for(int i = 0 ; i < result.size() ; i++){
//            orderList.add(JSONObject.toJavaObject(result.getJSONObject(i), Order.class));
//        }
//        System.out.println(orderList);
        return orderList;
    }
}
