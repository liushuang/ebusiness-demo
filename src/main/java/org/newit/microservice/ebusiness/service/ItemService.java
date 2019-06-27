package org.newit.microservice.ebusiness.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.newit.microservice.ebusiness.model.Item;
import org.newit.microservice.ebusiness.model.ItemWithVisit;
import org.newit.microservice.ebusiness.model.Order;
import org.newit.microservice.ebusiness.repository.ItemFeignClient;
import org.newit.microservice.ebusiness.repository.ItemRepository;
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
    private ItemRepository itemRepository;

    @Autowired
    private ItemFeignClient itemFeignClient;

    public Item getItemById(long itemId) {
        itemRepository.addItemVisit(itemId);
//        return itemRepository.getItemById(itemId);
        return itemFeignClient.getItemById(itemId);
    }

    public void insert(Item item) {
//
//        itemRepository.insert(item);
        itemFeignClient.insert(item);
    }


    public List<Item> getItemAllList() {
//        return itemRepository.getItemAllList();
        Map<String, Object> map = Maps.newHashMap();
        map.put("start",2);
        map.put("limit",2);
        map.put("param3", "from new it");
        return itemFeignClient.itemAllList(map);
    }

    public List<ItemWithVisit> getItemTopList() {
        return itemRepository.getTopItemList();
    }
}
