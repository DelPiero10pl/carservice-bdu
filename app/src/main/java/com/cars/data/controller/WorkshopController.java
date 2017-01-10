package com.cars.data.controller;

import com.cars.data.model.Workshop;

public class WorkshopController extends Controller<Workshop> {
    public WorkshopController() {
        super(Workshop.class);
    }

    public Workshop create(String name, String localization) {
        realm.beginTransaction();
        Workshop workshop = create();
        workshop.setName(name);
        workshop.setLocalization(localization);
        realm.commitTransaction();
        return workshop;
    }

    public Workshop update(Integer id, String name, String localization) {
        realm.beginTransaction();
        Workshop workshop = findByID(id);
        workshop.setName(name);
        workshop.setLocalization(localization);
        realm.commitTransaction();
        return workshop;
    }

}
