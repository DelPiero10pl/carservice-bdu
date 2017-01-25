package com.cars.data.controller;

import com.cars.data.model.*;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RepairController extends Controller<Repair> {

    public static final String REPAIR_ID = "repair_id";

    public RepairController() {
        super(Repair.class);
    }

    public Repair create(String name, Car car, Date date, Integer mileage, Workshop workshop, Double workshopCost, RealmList<Part> parts, Boolean planed) {
        realm.beginTransaction();
        Repair repair = create();
        repair.setName(name);
        repair.setCar(car);
        if(car.getMileage()<mileage && !planed)
            car.setMileage(mileage);
        repair.setDate(DateHelper.format(date));
        repair.setMilage(mileage);
        if(workshop != null) repair.setWorkshop(workshop);
        repair.setWorkshopCost(workshopCost);
        if(parts != null) {
            List<Part> copy = realm.copyToRealm(parts);
            RealmList<Part> ml = new RealmList<>();
            ml.addAll(copy);
            repair.setParts(ml);
        }
        repair.setPlaned(planed);
        realm.commitTransaction();
        return repair;
    }

    public Repair update(Integer id, String name, Car car, Date date, Integer mileage, Workshop workshop, Double workshopCost, RealmList<Part> parts, Boolean planed) {
        realm.beginTransaction();
        Repair update = realm.where(Repair.class).equalTo("id", id).findFirst();
        update.getParts().deleteAllFromRealm();
        update.setName(name);
        update.setWorkshopCost(workshopCost);
        update.setWorkshop(workshop);
        update.setDate(DateHelper.format(date));
        update.setMilage(mileage);
        update.setPlaned(planed);
        if(parts!=null) {
            List<Part> copy = realm.copyToRealm(parts);
            RealmList<Part> ml = new RealmList<>();
            ml.addAll(copy);
            update.setParts(ml);
        } else update.setParts(null);
        realm.commitTransaction();
        return update;
    }

    public boolean remove(int id) {
        realm.beginTransaction();
        Repair repair = findByID(id);
        RealmResults<Photo> photos = realm.where(Photo.class).equalTo("repair.id", repair.getId()).findAll();
        for (Photo photo:photos) {
            File file = new File(photo.getFilename());
            file.delete();
            photo.deleteFromRealm();
        }
        repair.deleteFromRealm();
        realm.commitTransaction();
        return true;
    }


}
