package com.cars.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.view.Menu;
import android.view.MenuItem;

public class AddHistoryRepair extends AppCompatActivity {
    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history_repair);
        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

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
}
