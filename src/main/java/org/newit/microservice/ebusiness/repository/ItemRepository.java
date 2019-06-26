package org.newit.microservice.ebusiness.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.newit.microservice.ebusiness.model.Item;
import org.newit.microservice.ebusiness.model.ItemWithVisit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

@Repository
public class ItemRepository {

    private static final String ITEM_SERVICE_PREFIX= "http://item-service/";
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Cacheable(value = "item", key = "'item_' + #itemId ")
    public Item getItemById(long itemId) {
        Item item = restTemplate.getForObject(ITEM_SERVICE_PREFIX +"item/" + itemId, Item.class);
        return item;
    }

    @CachePut(value = "item", key = "'item_' + #itemId ")
    public Item insert(Item item) {
        restTemplate.postForObject(ITEM_SERVICE_PREFIX + "item/insert", item, JSONObject.class);
        return item;
    }

    public List<Item> getItemAllList() {
        ResponseEntity<List<Item>>
                responseEntity = restTemplate.exchange(ITEM_SERVICE_PREFIX + "item/allList", HttpMethod.GET, null,
                                                       new ParameterizedTypeReference<List<Item>>() {});
        List<Item> itemList = responseEntity.getBody();
        return itemList;
    }

    public void addItemVisit(long itemId){
        redisTemplate.opsForZSet().incrementScore("topItemList", itemId, 1);
    }

    public List<ItemWithVisit> getTopItemList(){
        Set<TypedTuple<Integer>> typedTupleSet =  redisTemplate.opsForZSet().reverseRangeByScoreWithScores("topItemList", 0, Double.MAX_VALUE, 0,5);
        List<ItemWithVisit> result = Lists.newArrayList();
        Iterator<TypedTuple<Integer>> iterator = typedTupleSet.iterator();
        while(iterator.hasNext()){
            TypedTuple<Integer> next = iterator.next();
            long itemId = next.getValue().longValue();
            int visit = next.getScore().intValue();
            Item item = getItemById(itemId);
            ItemWithVisit itemWithVisit = new ItemWithVisit();
            itemWithVisit.setItem(item);
            itemWithVisit.setVisit(visit);
            result.add(itemWithVisit);
        }

        return result;
    }
}
