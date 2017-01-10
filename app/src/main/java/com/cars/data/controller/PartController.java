package com.cars.data.controller;

import com.cars.data.model.Part;

public class PartController extends Controller<Part> {
    public PartController() {
        super(Part.class);
    }

    public Part create(String name, Double price) {
        realm.beginTransaction();
        Part part = create();
        part.setName(name);
        part.setPrice(price);
        realm.commitTransaction();
        return part;
    }

    public Part update(Integer id, String name, Double price) {
        realm.beginTransaction();
        Part part = findByID(id);
        part.setName(name);
        part.setPrice(price);
        realm.commitTransaction();
        return part;
    }
}
