package com.cars.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import io.realm.Realm;

public class SettingsApp extends AppCompatActivity {

    private Realm realm;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_app);
        realm = Realm.getDefaultInstance();
        context = this;
        Button cleanDB = (Button) findViewById(R.id.clean_db);
        cleanDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).setMessage("Wyczyścić bazę?").setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        realm.beginTransaction();
                        realm.deleteAll();
                        realm.commitTransaction();
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
                alertDialog.show();
            }
        });
    }
}
