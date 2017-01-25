package com.cars.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import com.cars.CarsApplication;
import com.cars.data.controller.*;
import com.cars.data.model.*;
import com.cars.data.model.Car;
import com.cars.ui.adapter.PartRecyclerViewAdapter;
import com.cars.ui.adapter.PhotoRecycleViewAdapter;
import com.cars.ui.dialog.DialogPartEdit;
import com.cars.ui.dialog.DialogWorkshop;
import com.cars.ui.flow.Router;
import io.realm.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddHistoryRepair extends AppCompatActivity {
    private static final int CAMERA_INTENT_REQUEST = 8001;
    TabHost tabHost;
    private ListView parts;
    private Realm memDB;
    private Activity context;
    private ImageButton addNewWorkshop;
    private Realm realm;
    private Spinner workShopSpinner;
    private RealmResults<Workshop> workshopResult;
    private ArrayAdapter<String> dataAdapter;
    private ImageButton takePhoto;
    private String imgPath;
    private String curPhtot;
    //private ArrayList<String> photoList;
    private Button save;
    private EditText millage;
    private EditText name;
    private EditText date;
    private Intent intent;
    private Switch workshopIsEnable;
    private ArrayList<Workshop> workshopsList;
    private EditText workshopCost;
    private RealmRecyclerView recycleview;
    private Calendar myCalendar;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private RecyclerView photosRecycleView;
    private Integer nextPhotoID;
    private List<String> fileToDel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history_repair);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        context = this;
        CarsApplication application = (CarsApplication) getApplication();
        memDB = Realm.getInstance(application.memCfg);
        memDB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm mem) {
                mem.deleteAll();
            }
        });
        realm = Realm.getDefaultInstance();
        host.setup();
        initUI();
        fileToDel = new ArrayList<>();
        //photoList = new ArrayList<>();
        myCalendar = Calendar.getInstance();
        intent = getIntent();
        nextPhotoID = new PhotoController().nexID();
        editView();
        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Opis");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Opis");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Części");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Części");
        host.addTab(spec);


        //Tab 2
        spec = host.newTabSpec("Warsztat");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Warsztat");
        host.addTab(spec);


        //Tab 2
        spec = host.newTabSpec("Zdjęcia");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Zdjęcia");
        host.addTab(spec);
        
        for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
            TextView x = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            x.setTextSize(9);
        }


        workshopResult = realm.where(Workshop.class).findAll();


        workShopSpinner = (Spinner) findViewById(R.id.workshop_spiner);
        loadAdapter();
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm element) {
                loadAdapter();
            }
        });


        addNewWorkshop = (ImageButton) findViewById(R.id.new_workshop);
        addNewWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogWorkshop dialogWorkshop = new DialogWorkshop(context);

                dialogWorkshop.build(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText name = (EditText) dialogWorkshop.getDialogView().findViewById(R.id.name_workshop);
                        EditText adress = (EditText) dialogWorkshop.getDialogView().findViewById(R.id.workshop_address);
                        if(ValidationHelper.required(name, adress)) {
                            new WorkshopController().create(name.getText().toString(), adress.getText().toString());
                        } else {
                            Toast.makeText(context, getString(R.string.validate_error), Toast.LENGTH_SHORT).show();
                        }
                        dialogInterface.dismiss();

                    }
                }).create().show();
            }
        });
        recycleview = (RealmRecyclerView) findViewById(R.id.realm_recycler_view_form_parts);

        Button btn = (Button) findViewById(R.id.add_new_part);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPartEdit b = new DialogPartEdit(context);
                final EditText name = (EditText) b.getDialogView().findViewById(R.id.part_name);
                final EditText cost = (EditText) b.getDialogView().findViewById(R.id.cost_part);
                b.build(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        memDB.beginTransaction();
                        Part tmp = memDB.createObject(Part.class, new Random().nextInt());
                        tmp.setName(name.getText().toString());
                        tmp.setPrice(Double.parseDouble(cost.getText().toString()));
                        memDB.commitTransaction();
                        dialogInterface.dismiss();
                    }
                }).create().show();
            }
        });
        RealmResults<Part> tmp = memDB.where(Part.class).findAll();
        PartRecyclerViewAdapter adapter = new PartRecyclerViewAdapter(this, tmp, true, true, memDB);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleview.setAdapter(adapter);



        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dispatchTakePictureIntent();
            }
        });

        save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        DatePicker datePicker = new DatePicker(this);
        final DatePickerDialog.OnDateSetListener dateListner = new DatePickerDialog.OnDateSetListener() {

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
                if(ValidationHelper.required(name, date, millage)) {
                    RealmResults<Part> newParts = memDB.where(Part.class).findAll();
                    RealmList<Part> list = new RealmList<>();
                    Integer nexID = new PartController().nexID();
                    for (Part part:
                         newParts) {
                        Part newPart = new Part();
                        newPart.setId(nexID);
                        newPart.setName(part.getName());
                        newPart.setPrice(part.getPrice());
                        list.add(newPart);
                        nexID++;
                    }


                    Workshop workshop = null;
                    Double workShopCostVal = 0d;
                    if(workshopIsEnable.isChecked()) {
                        int pos = workShopSpinner.getSelectedItemPosition();
                        workshop = workshopsList.get(pos);
                        workShopCostVal = Double.valueOf(workshopCost.getText().toString());
                    }

                    Car car = realm.where(Car.class).equalTo("id", intent.getIntExtra(CarController.CAR_ID, 0)).findFirst();
                    int id=0;
                    if(intent.hasExtra(RepairController.REPAIR_ID)) {

                        Repair update = new RepairController().update(intent.getIntExtra(RepairController.REPAIR_ID, 0),
                                name.getText().toString(),
                                null,
                                Calendar.getInstance().getTime(),
                                Integer.parseInt(millage.getText().toString()),
                                workshop,workShopCostVal,list,false);
                        realm.beginTransaction();
                        realm.where(Photo.class).equalTo("repair.id", update.getId()).findAll().deleteAllFromRealm();
                        RealmResults<Photo> photos = memDB.where(Photo.class).findAll();
                        for (Photo photo:
                                photos) {
                            Photo nm = realm.copyToRealm(photo);
                            nm.setFilename(photo.getFilename());
                            nm.setRepair(update);
                        }
                        realm.commitTransaction();
                        id = update.getId();
                    } else {
                        Repair newRepair = new RepairController().create(name.getText().toString(),
                                car,
                                Calendar.getInstance().getTime(),
                                Integer.parseInt(millage.getText().toString()),
                                workshop,
                                workShopCostVal,
                                list,
                                false);
                        RealmResults<Photo> photos = memDB.where(Photo.class).findAll();
                        realm.beginTransaction();
                        for (Photo photo:
                                photos) {
                            Photo nm = realm.copyToRealm(photo);
                            nm.setFilename(photo.getFilename());
                            nm.setRepair(newRepair);
                        }
                        realm.commitTransaction();
                        id = newRepair.getId();
                    }
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            for (String s:
                                    fileToDel) {
                                File f = new File(s);
                                if(f.exists()) {
                                    f.delete();
                                }
                            }
                            return null;
                        }
                    }.execute();
                    Toast.makeText(getApplicationContext(), "Zapisano", Toast.LENGTH_LONG).show();
                    Router.showRepair(context, id);
                } else Toast.makeText(getApplicationContext(), "Wprowadź poprawnie dane", Toast.LENGTH_LONG).show();

            }
        });
    }




    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        curPhtot = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void loadAdapter() {
        List<String> lables = new ArrayList<>();
        workshopsList = new ArrayList<>();
        for (Workshop lable :
                workshopResult) {
            workshopsList.add(lable);
            lables.add(lable.getName());
        }
        dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, lables);
        workShopSpinner.setAdapter(dataAdapter);
    }

    private void initUI() {
        workshopIsEnable = (Switch) findViewById(R.id.workshop_is_enable);
        workshopIsEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(workshopIsEnable.isChecked())
                findViewById(R.id.workshop_holder).setVisibility(View.VISIBLE);
                else
                findViewById(R.id.workshop_holder).setVisibility(View.GONE);
            }
        });
        workshopCost = (EditText) findViewById(R.id.workshop_cost);
        photosRecycleView = (RecyclerView) findViewById(R.id.photo_list);
        PhotoRecycleViewAdapter adapter = new PhotoRecycleViewAdapter(this, memDB.where(Photo.class).findAll(), true, true){
            @Override
            public void click(final Integer id) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).setMessage("Usunąć?").setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        memDB.beginTransaction();
                        Photo tmp = memDB.where(Photo.class).equalTo("id", id).findFirst();
                        fileToDel.add(tmp.getFilename());
                        tmp.deleteFromRealm();
                        memDB.commitTransaction();
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
        };

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        photosRecycleView.setLayoutManager(llm);
        photosRecycleView.setAdapter(adapter);

        name = (EditText) findViewById(R.id.name);
        date = (EditText) findViewById(R.id.date);
        millage = (EditText) findViewById(R.id.millage);
        millage.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "9000000")});
        takePhoto = (ImageButton) findViewById(R.id.take_photo);

    }

    private void editView() {
        if(intent.hasExtra(RepairController.REPAIR_ID)) {
            int repairID = intent.getIntExtra(RepairController.REPAIR_ID, 0);
            Repair edit = new RepairController().findByID(repairID);
            date.setText(edit.getDate());
            millage.setText(edit.getMilage().toString());
            name.setText(edit.getName());
            if(edit.getWorkshop()!=null) {
                workshopIsEnable.setChecked(true);
                findViewById(R.id.workshop_holder).setVisibility(View.VISIBLE);
                workshopCost.setText(String.valueOf(edit.getWorkshopCost()));
            }
            RealmResults<Photo> photos = realm.where(Photo.class).equalTo("repair.id", repairID).findAll();
            memDB.beginTransaction();
            memDB.copyToRealm(edit.getParts());
            //memDB.copyToRealm(photos.get(0));
            //Log.d("VV", memDB.where(Photo.class).findAll().toString());
            //memDB.copyToRealm(photos);

            for (Photo pp: photos) {
                Photo copy = memDB.createObject(Photo.class, pp.getId());
                copy.setFilename(pp.getFilename());
                memDB.insert(copy);
            }

            memDB.commitTransaction();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK) {
            //photoList.add(curPhtot);
            memDB.beginTransaction();
            Photo loc = memDB.createObject(Photo.class, nextPhotoID);
            loc.setFilename(curPhtot);
            nextPhotoID++;
            memDB.commitTransaction();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        memDB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm mem) {
                mem.deleteAll();
            }
        });



    }
}
