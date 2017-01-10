package com.cars.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.cars.data.controller.CarController;
import com.cars.data.model.Car;

import static com.cars.data.controller.CarController.CAR_ID;

public class AddCar extends AppCompatActivity {

    Context context;

    private Button cancel,save;
    private EditText mark,model,year,engine,mileage;
    private Spinner fuelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        context = this;
        initUI();

        save.setOnClickListener(saveCar());
        cancel.setOnClickListener(back());
    }

    private View.OnClickListener saveCar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isvValid()) {
                    Double cap = Double.parseDouble(engine.getText().toString());
                    Integer mileageI = Integer.parseInt(mileage.getText().toString());
                    Integer yearI = Integer.parseInt(year.getText().toString());
                    long fTyp = fuelType.getSelectedItemId()+1;
                    Car car = new CarController().create(mark.getText().toString(), model.getText().toString(), cap, (int) fTyp, mileageI, yearI);
                    Intent intent = new Intent(context, com.cars.ui.Car.class);
                    intent.putExtra(CAR_ID, car.getId());
                    context.startActivity(intent);
                } else Toast.makeText(context, getString(R.string.validate_error), Toast.LENGTH_LONG).show();
            }
        };
    }

    private View.OnClickListener back() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CarList.class);
                context.startActivity(intent);
            }
        };
    }

    private boolean isvValid() {
        return ValidationHelper.required(mark,model,year,engine,mileage);
    }

    private void initUI() {
        cancel = (Button) findViewById(R.id.cancel);
        save = (Button) findViewById(R.id.save);

        mark = (EditText) findViewById(R.id.mark);
        model = (EditText) findViewById(R.id.model);
        year = (EditText) findViewById(R.id.year);
        engine = (EditText) findViewById(R.id.engine);
        mileage = (EditText) findViewById(R.id.mileage);
        fuelType = (Spinner) findViewById(R.id.fuel_type);
    }


}
