package com.cars.data.controller;

import com.cars.data.model.Car;
import com.cars.data.model.Photo;
import com.cars.data.model.Repair;
import io.realm.RealmResults;

public class CarController extends Controller<Car> {

    public static final String CAR_ID = "car_id";

    public CarController() {
        super(Car.class);
    }

    public Car create(String mark, String model, Double capacity, Integer fuelTyp, Integer mileage, Integer productionYear) {
        realm.beginTransaction();
        Car car = create();
        car.setMark(mark);
        car.setModel(model);
        car.setCapacity(capacity);
        car.setFuelTyp(fuelTyp);
        car.setMileage(mileage);
        car.setProductionYear(productionYear);
        realm.commitTransaction();
        return car;
    }

    public Car update(Integer id, String mark, String model, Double capacity, Integer fuelTyp, Integer mileage, Integer productionYear) {
        realm.beginTransaction();
        Car car = findByID(id);
        car.setMark(mark);
        car.setModel(model);
        car.setCapacity(capacity);
        car.setFuelTyp(fuelTyp);
        car.setMileage(mileage);
        car.setProductionYear(productionYear);
        realm.commitTransaction();
        return car;
    }

    public boolean remove(Integer id) {
        realm.beginTransaction();
        Car car = findByID(id);
        RealmResults<Repair> resultRepair = realm.where(Repair.class).equalTo("car.id", car.getId()).findAll();
        for (Repair repair:resultRepair) {
            realm.where(Photo.class).equalTo("repair.id", repair.getId()).findAll().deleteAllFromRealm();
            repair.getParts().deleteAllFromRealm();
            repair.deleteFromRealm();
        }
        car.deleteFromRealm();
        realm.commitTransaction();
        return true;
    }


}
