package com.cars.ui;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.*;
import com.cars.data.controller.RepairController;
import com.cars.data.model.*;
import com.cars.data.model.Car;
import com.cars.ui.adapter.spiner.CarArrayAdapter;
import com.cars.ui.flow.Router;
import io.realm.Realm;
import io.realm.RealmResults;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddRepair extends AppCompatActivity {

    private Spinner carList;
    private EditText date;
    private EditText name;
    //private DatePicker datePicker;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener dateListner;
    private AddRepair context;
    private Realm realm;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repair);
        myCalendar = Calendar.getInstance();
        context = this;
        realm = Realm.getDefaultInstance();
        initUI();
    }

    private void initUI() {
        save = (Button) findViewById(R.id.save);
        carList = (Spinner) findViewById(R.id.car_list);
        RealmResults<Car> list = realm.where(Car.class).findAll();
        final CarArrayAdapter adapter = new CarArrayAdapter(this, list);
        carList.setAdapter(adapter);
        name = (EditText) findViewById(R.id.name);
        date = (EditText) findViewById(R.id.date);
        //datePicker = new DatePicker(this);
        dateListner = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date.setText(sdf.format(myCalendar.getTime()));
            }
        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, dateListner, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date convDate = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat(DateHelper.dateFormat);
                try {
                    convDate = dateFormat.parse(date.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new RepairController().create(name.getText().toString(), (Car) carList.getSelectedItem(), convDate,
                        0, null,
                        0d, null, true);
                Toast.makeText(context, "Zaplanowano", Toast.LENGTH_SHORT).show();
                Router.showCarsList(context);
            }
        });






    }
}
