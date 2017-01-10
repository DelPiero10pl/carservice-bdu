package com.cars.data.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Repair extends RealmObject {
    @PrimaryKey
    private Integer id;
    private String name;
    private String date;
    private  double workshopCost;
    private  Integer milage;
    private  Boolean planed;

    private Workshop workshop;
    private Car car;

    private RealmList<Part> parts;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWorkshopCost() {
        return workshopCost;
    }

    public void setWorkshopCost(double workshopCost) {
        this.workshopCost = workshopCost;
    }

    public Integer getMilage() {
        return milage;
    }

    public void setMilage(Integer milage) {
        this.milage = milage;
    }

    public Boolean getPlaned() {
        return planed;
    }

    public void setPlaned(Boolean planed) {
        this.planed = planed;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public RealmList<Part> getParts() {
        return parts;
    }

    public void setParts(RealmList<Part> parts) {
        this.parts = parts;
    }
}
