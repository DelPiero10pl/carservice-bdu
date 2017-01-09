package com.cars.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Part extends RealmObject {

    @PrimaryKey
    private Integer partID;
    private String name;
    private Double price;

    public Integer getPartID() {
        return partID;
    }

    public void setPartID(Integer partID) {
        this.partID = partID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
