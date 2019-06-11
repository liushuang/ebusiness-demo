package org.newit.microservice.ebusiness.service;

import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.newit.microservice.ebusiness.model.Item;
import org.newit.microservice.ebusiness.model.Order;
import org.newit.microservice.ebusiness.model.User;
import org.newit.microservice.ebusiness.repository.OrderRepository;
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
    private OrderRepository orderRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    public Order getOrderById(Long orderId) {
        Order order = orderRepository.getOrderById(orderId);
        return order;
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
        orderRepository.insert(order);
    }

    public List<Order> getOrderListByBuyerId(long buyerId) {
        return orderRepository.getOrderListByBuyerId(buyerId);
    }

    public List<Order> getOrderListBySellerId(long sellerId) {
        return orderRepository.getOrderListBySellerId(sellerId);
    }

}
