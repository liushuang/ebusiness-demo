package org.newit.microservice.ebusiness.service;

import java.util.List;

import org.newit.microservice.ebusiness.model.Item;
import org.newit.microservice.ebusiness.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

@Service
public class ItemService {

    @Autowired
    private RestTemplate restTemplate;

    public Item getItemById(long itemId) {
        Item item = restTemplate.getForObject("http://localhost:29610/item/" + itemId, Item.class);
        return item;
    }

    public void insert(Item item) {
        restTemplate.postForObject("http://localhost:29610/item/insert", item, JSONObject.class);
    }

    public List<Item> getItemAllList() {
        ResponseEntity<List<Item>>
                responseEntity = restTemplate.exchange("http://localhost:29610/item/allList", HttpMethod.GET, null,
                                                       new ParameterizedTypeReference<List<Item>>() {});
        List<Item> itemList = responseEntity.getBody();
        return itemList;
    }
}
