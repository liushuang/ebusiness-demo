package org.newit.microservice.ebusiness.service;

import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.newit.microservice.ebusiness.model.Item;
import org.newit.microservice.ebusiness.model.Order;
import org.newit.microservice.ebusiness.model.User;
import org.newit.microservice.ebusiness.view.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    public Order getOrderById(Long orderId) {
        return restTemplate.getForObject("http://localhost:19610/order/" + orderId, Order.class);
    }

    public OrderView getOrderView(Order order){
        OrderView orderView = new OrderView();
        orderView.setOrder(order);
        orderView.setBuyer(userService.getUserById(order.getBuyerId()));
        orderView.setSeller(userService.getUserById(order.getSellerId()));
        orderView.setItem(itemService.getItemById(order.getItemId()));
        return orderView;
    }
    public void createOrder(Long itemId, Long buyerId) {
        Item item = itemService.getItemById(itemId);
        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setSellerId(item.getSellerId());
        order.setItemId(item.getId());
        order.setPrice(item.getPrice());
        order.setCreatedTime(Calendar.getInstance().getTime());
        restTemplate.postForObject("http://localhost:19610/add/order",order,String.class);
    }

    public List<Order> getOrderListByBuyerId(long buyerId) {
        ResponseEntity<List<Order>> responseEntity = restTemplate.exchange("http://localhost:19610/order/buyerList?buyerId=" + buyerId, HttpMethod.GET, null,
                                                                      new ParameterizedTypeReference<List<Order>>() {});
        List<Order> orderList = responseEntity.getBody();
//        JSONArray result  =   restTemplate.getForObject("http://localhost:19610/order/buyerList?buyerId=" + buyerId, JSONArray.class);
//        List<Order> orderList = Lists.newArrayList();
//        for(int i = 0 ; i < result.size() ; i++){
//            orderList.add(JSONObject.toJavaObject(result.getJSONObject(i), Order.class));
//        }
//        System.out.println(orderList);
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
