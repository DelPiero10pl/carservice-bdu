package com.cars.ui.flow;

import android.content.Context;
import android.content.Intent;
import com.cars.data.controller.CarController;
import com.cars.ui.Car;

public class CarFlow {

    public static void showCar(Context ctx, Integer id) {
        Intent i = new Intent(ctx, Car.class);
        i.putExtra(CarController.CAR_ID, id);
        ctx.startActivity(i);
    }



}
