package com.jacobbieker.exoplanets.ui;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jacobbieker.exoplanets.beans.System;
import com.jacobbieker.exoplanets.R;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Jacob on 1/9/14.
 */
public class SystemActivity extends Activity {

    private ListView listView;
    private List<System> systems;
    private ArrayAdapter<System> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        EventBus.getDefault().register(this);

        if (findViewById(R.id.first_container) != null && findViewById(R.id.second_container) != null) {
            if (savedInstanceState == null) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.first_container, new SystemFragment());
                fragmentTransaction.add(R.id.second_container, new DetailFragment());
                fragmentTransaction.commit();
            }
        } else if (findViewById(R.id.first_container) != null) {
            if (savedInstanceState == null) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.first_container, new SystemFragment());
                fragmentTransaction.commit();
            }

        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

}

