package com.cars.data.controller;

import com.cars.data.model.Car;

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


}
