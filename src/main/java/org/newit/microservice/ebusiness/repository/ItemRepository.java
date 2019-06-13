package org.newit.microservice.ebusiness.repository;

import java.util.List;

import org.newit.microservice.ebusiness.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

@Repository
public class ItemRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Cacheable(value = "item", key = "'item_' + #itemId ")
    public Item getItemById(long itemId) {
        Item item = restTemplate.getForObject("http://localhost:29610/item/" + itemId, Item.class);
        return item;
    }

    @CachePut(value = "item", key = "'item_' + #itemId ")
    public Item insert(Item item) {
        restTemplate.postForObject("http://localhost:29610/item/insert", item, JSONObject.class);
        return item;
    }

    public List<Item> getItemAllList() {
        ResponseEntity<List<Item>>
                responseEntity = restTemplate.exchange("http://localhost:29610/item/allList", HttpMethod.GET, null,
                                                       new ParameterizedTypeReference<List<Item>>() {});
        List<Item> itemList = responseEntity.getBody();
        return itemList;
    }
}
