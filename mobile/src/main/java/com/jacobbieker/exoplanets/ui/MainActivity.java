/*
 * The MIT License (MIT)
 *
 * Copyright (c) $year. Jacob Bieker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jacobbieker.exoplanets.ui;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jacobbieker.exoplanets.R;
import com.jacobbieker.exoplanets.eventbus.ItemSelectedEvent;
import com.jacobbieker.exoplanets.settings.SettingsActivity;
import com.jacobbieker.exoplanets.xml.JsonPullerService;

import de.greenrobot.event.EventBus;

public class MainActivity extends Activity {

    public EventBus controlBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO Make sure this works
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_main);
        controlBus = new EventBus();
        controlBus.register(this);
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);


        String gitHubURL = "https://api.github.com/repos/OpenExoplanetCatalogue/open_exoplanet_catalogue/contents/systems?ref=master";//Change if URL changes to download thing
        //Start Service using Intent
        //TODO Make Sure This Is Right
        Log.i("GitHubService", "Intent About to be Passed");
        Intent gitHubDownload = new Intent(getBaseContext(), JsonPullerService.class);
        gitHubDownload.putExtra("URL1", gitHubURL);
        getBaseContext().startService(gitHubDownload);
        Log.i("GitHubService", "Intent About Passed");


        //Adding GitHubHeadless Fragment
       /* FragmentTransaction fT = getFragmentManager().beginTransaction();
        fT.add(new GitHubHeadless(), "GitHubHeadless");
        getFragmentManager().findFragmentByTag("GitHubHeadless").setRetainInstance(true);
        fT.commit();
        Log.i("GitHubHeadless", "Headless Fragment Added Successfully");*/

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
        controlBus.unregister(this);
        super.onDestroy();
    }

    public void onEvent(ItemSelectedEvent event) {
        Log.i("EventBus MainActivity event", " ItemSelectedEvent called");
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
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
