package org.newit.microservice.ebusiness.repository;

import com.alibaba.fastjson.JSONObject;
import org.newit.microservice.ebusiness.model.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "item-service", fallback = ItemFeiginClientFallback.class)
public interface ItemFeignClient {

    @RequestMapping("/item/{itemId}")
    Item getItemById(@PathVariable long itemId);

    @RequestMapping(value = "/item/allList", method = RequestMethod.GET)
    public List<Item> itemAllList(@RequestParam Map<String, Object> map);

    @RequestMapping(value = "/item/insert", method = RequestMethod.POST)
    public JSONObject insert(@RequestBody Item item);
}
