package com.cars.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Workshop extends RealmObject {
    @PrimaryKey
    private Integer id;
    private String name;
    private String localization;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }
}
