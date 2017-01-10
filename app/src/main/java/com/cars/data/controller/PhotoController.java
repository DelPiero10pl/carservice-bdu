package com.cars.data.controller;

import com.cars.data.model.Photo;
import com.cars.data.model.Repair;

import java.io.File;

public class PhotoController extends Controller<Photo> {
    public PhotoController() {
        super(Photo.class);
    }

    public Photo create(File file, Repair repair) {
        realm.beginTransaction();
        Photo photo = create();
        photo.setFilename(file.getAbsolutePath());
        photo.setRepair(repair);
        realm.commitTransaction();
        return photo;
    }

    public Photo update(Integer id, Repair repair) {
        realm.beginTransaction();
        Photo photo = findByID(id);
        photo.setRepair(repair);
        realm.commitTransaction();
        return photo;
    }
}
