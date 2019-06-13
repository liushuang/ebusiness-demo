package org.newit.microservice.ebusiness.model;

import lombok.Data;

@Data
public class ItemWithVisit {
    private Item item;
    private int visit;
}
