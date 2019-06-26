package org.newit.microservice.ebusiness.repository;

import org.newit.microservice.ebusiness.model.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "item-service",url = "")
public interface ItemFeignClient {

    @RequestMapping("/item/{itemId}")
    Item getItemById(@PathVariable long itemId);
}
