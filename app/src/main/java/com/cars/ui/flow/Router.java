package com.cars.ui.flow;

import android.content.Context;
import android.content.Intent;
import com.cars.data.controller.CarController;
import com.cars.data.controller.RepairController;
import com.cars.data.model.Repair;
import com.cars.ui.*;

public class Router {

    public static void showCar(Context ctx, Integer id) {
        Intent i = new Intent(ctx, Car.class);
        i.putExtra(CarController.CAR_ID, id);
        ctx.startActivity(i);
    }

    public static void showEitCar(Context ctx, Integer id) {
        Intent i = new Intent(ctx, AddCar.class);
        i.putExtra(CarController.CAR_ID, id);
        ctx.startActivity(i);
    }

    public static void addHistory(Context ctx, Integer id) {
        Intent intent = new Intent(ctx, AddHistoryRepair.class);
        intent.putExtra(CarController.CAR_ID, id);
        ctx.startActivity(intent);
    }

    public static void toEditRepair(Context ctx, Integer id) {
        Intent intent = new Intent(ctx, AddHistoryRepair.class);
        intent.putExtra(RepairController.REPAIR_ID, id);
        ctx.startActivity(intent);
    }

    public static void showRepair(Context ctx, Integer id) {
        Intent intent = new Intent(ctx, Servicing.class);
        intent.putExtra(RepairController.REPAIR_ID, id);
        ctx.startActivity(intent);
    }

    public static void showCarsList(Context ctx) {
        Intent intent = new Intent(ctx, CarList.class);
        ctx.startActivity(intent);
    }



}
