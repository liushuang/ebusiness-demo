package org.newit.microservice.ebusiness.repository;

import com.alibaba.fastjson.JSONObject;
import org.newit.microservice.ebusiness.model.Item;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ItemFeiginClientFallback implements ItemFeignClient{
    @Override
    public Item getItemById(long itemId) {
        Item item = new Item();
        item.setName("from ItemFeiginClientFallback");
        return item;
    }

    @Override
    public List<Item> itemAllList(Map<String, Object> map) {
        return null;
    }

    @Override
    public JSONObject insert(Item item) {
        return null;
    }
}
