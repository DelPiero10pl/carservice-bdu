package com.cars.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Car extends RealmObject {
    @PrimaryKey
    private Integer id;
    private String mark;
    private String model;
    private Integer productionYear;
    private Integer mileage;
    private Double capacity;
    private Integer fuelTyp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Integer getFuelTyp() {
        return fuelTyp;
    }

    public void setFuelTyp(Integer fuelTyp) {
        this.fuelTyp = fuelTyp;
    }
}
