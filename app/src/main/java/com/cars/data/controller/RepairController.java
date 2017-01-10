package com.cars.data.controller;

import com.cars.data.model.*;
import io.realm.Realm;
import io.realm.RealmList;

import java.util.Date;

public class RepairController extends Controller<Repair> {
    public RepairController() {
        super(Repair.class);
    }

    public Repair create(String name, Car car, Date date, Integer mileage, Workshop workshop, Double workshopCost, RealmList<Part> parts, Boolean planed) {
        realm.beginTransaction();
        Repair repair = create();
        repair.setName(name);
        repair.setCar(car);
        repair.setDate(DateHelper.format(date));
        repair.setMilage(mileage);
        if(workshop != null) repair.setWorkshop(workshop);
        repair.setWorkshopCost(workshopCost);
        if(parts != null) repair.setParts(parts);
        repair.setPlaned(planed);
        realm.commitTransaction();
        return repair;
    }

    public Repair update(Integer id, String name, Car car, Date date, Integer mileage, Workshop workshop, Double workshopCost, RealmList<Part> parts, Boolean planed) {
        realm.beginTransaction();
        Repair repair = findByID(id);
        repair.setName(name);
        repair.setCar(car);
        repair.setDate(DateHelper.format(date));
        repair.setMilage(mileage);
        if(workshop != null) repair.setWorkshop(workshop);
        repair.setWorkshopCost(workshopCost);
        if(parts != null) repair.setParts(parts);
        repair.setPlaned(planed);
        realm.commitTransaction();
        return repair;
    }


}
