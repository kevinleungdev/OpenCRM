package com.opencrm.app.json;

import com.fasterxml.jackson.annotation.JsonView;

public class Item {
    @JsonView(Views.Public.class)
    private Integer id;

    @JsonView(Views.Public.class)
    private String itemName;

    @JsonView(Views.Internal.class)
    private String ownerName;

    public Item(Integer id, String itemName, String ownerName) {
        this.id = id;
        this.itemName = itemName;
        this.ownerName = ownerName;
    }
}
