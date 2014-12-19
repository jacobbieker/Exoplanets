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
import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.jacobbieker.exoplanets.database.DatabaseHelper;
import com.jacobbieker.exoplanets.database.SystemDataSource;
import com.jacobbieker.exoplanets.eventbus.ItemSelectedEvent;

import com.jacobbieker.exoplanets.R;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Bieker on 12/6/13.
 */
public class DetailFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private ArrayList<String> results = new ArrayList<String>();
    private String tablePlanetsName = "Planets";
    private SQLiteDatabase newDB;
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    public SystemDataSource dataSource;
    DatabaseHelper databaseHelper = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        dataSource = new SystemDataSource(getActivity());
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.system_list_fragment, container, false);
        Log.i("Fragment", "OnCreateView(): View Created");
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        databaseHelper = new DatabaseHelper(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newDB = databaseHelper.getWritableDatabase();
        Log.i("DetailFragment", "openAndViewDatabase called");
        //openAndViewDatabase();
        Log.i("DetailFragment", "updatePlanetView Called");
        //updatePlanetView(mCurrentPosition);
        Log.i("DetailFragment", "displayResultList Called");
        //displayResultList();
        //ListView listView = (ListView)getListView().findViewById(android.R.id.list);
        //listView.setOnItemClickListener(this);
        //Log.i("ListView", "setOnItemCLickListener called and set");
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**********************************************************************************************************************************
     * Below are methods that do not relate to Fragment lifecycle
     */

    /**
     * This method sets a List Adapter with the results from openAndViewDatabase()
     *
     */
    private void displayResultList() {
        Log.i("displayResultList", "setListAdapterCalled");
        setListAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item, results));
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
        // TODO Auto-generated method stub
        String item = adapter.getItemAtPosition(position).toString();
        Log.i("OnItemClick", "String Item found:" + position);
        Toast.makeText(getActivity().getApplicationContext(), "Click Item Position: " + item, Toast.LENGTH_LONG);
        Log.i("OnItemClick", "Toast Called: " + item);

    }

    private ArrayList<String> eventResults = new ArrayList<String>();
    public void ItemSelectedSystemName(ItemSelectedEvent event) {
        Cursor mCursor = newDB.rawQuery("SELECT * FROM Systems WHERE id = ?",  new String[] {String.valueOf(event.getItemSelected().getParentId())});
        String[] ColumnNames = mCursor.getColumnNames();
        mCursor.moveToFirst();
        for (int row = 0; row < mCursor.getCount(); row++) {
            StringBuilder builder = new StringBuilder();
            for (String ColumnName : ColumnNames) {
                if (ColumnName.equals("name")) {
                    builder.append(mCursor.getString(mCursor.getColumnIndex("name")));
                }
            }
            eventResults.add(builder.toString());
            mCursor.moveToNext();
        }
        mCursor.close();
    }

    /**
     * Listens for EventBus event and update ListView.
     * @param event
     */
    public void onEvent(ItemSelectedEvent event){
        Log.i("EventBus DetailFrag event", "ItemSelectedEvent called");
        Log.i("EventBus Cursor", "Event toString: " + String.valueOf(event.getItemSelected().getParentId()));
        ItemSelectedSystemName(event);
        Cursor eventCursor = newDB.rawQuery("SELECT * FROM Planets WHERE name = ?", new String[] {"%" + eventResults.toString() + "%"});
        Log.i("EventBus: Cursor Size", "" + eventCursor.getCount());
        String [] ColumnNames = eventCursor.getColumnNames();

        eventCursor.moveToFirst();
        Log.i("EventBus Cursor", "MoveToFirst Called");
        for (int planetRow = 0; planetRow < eventCursor.getCount(); planetRow++) {
            //Log.i("EventBus Cursor Rows", "Rows: " + planetRow);
            StringBuilder builder = new StringBuilder();
            for (String ColumnName : ColumnNames) {
                //Log.d("ColumnName", "" + ColumnName);
                if(eventCursor.getString(eventCursor.getColumnIndexOrThrow(ColumnName)) != null) {
                    if (ColumnName.equals("name")) {
                        builder.append("Name: ").append(eventCursor.getString(eventCursor.getColumnIndex("name"))).append("\n");
                    } else if (ColumnName.equals("list_type")) {
                        builder.append("List: ").append(eventCursor.getString(eventCursor.getColumnIndex("list_type"))).append("\n");
                    } else if (ColumnName.equals("mass")) {
                        builder.append("Mass: ").append(eventCursor.getString(eventCursor.getColumnIndex("mass"))).append("\n");
                    } else if (ColumnName.equals("radius")) {
                        builder.append("Radius: ").append(eventCursor.getString(eventCursor.getColumnIndex("radius"))).append("\n");
                    } else if (ColumnName.equals("temperature")) {
                        builder.append("Temperature: ").append(eventCursor.getString(eventCursor.getColumnIndex("temperature"))).append("\n");
                    } else if (ColumnName.equals("period")) {
                        builder.append("Period: ").append(eventCursor.getString(eventCursor.getColumnIndex("period"))).append("\n");
                    } else if (ColumnName.equals("semi_major_axis")) {
                        builder.append("Semi-Major Axis: ").append(eventCursor.getString(eventCursor.getColumnIndex("semi_major_axis"))).append("\n");
                    } else if (ColumnName.equals("eccentricity")) {
                        builder.append("Eccentricity: ").append(eventCursor.getString(eventCursor.getColumnIndex("eccentricity"))).append("\n");
                    } else if (ColumnName.equals("inclination")) {
                        builder.append("Inclination: ").append(eventCursor.getString(eventCursor.getColumnIndex("inclination"))).append("\n");
                    } else if (ColumnName.equals("periastron")) {
                        builder.append("Periastron: ").append(eventCursor.getString(eventCursor.getColumnIndex("periastron"))).append("\n");
                    } else if (ColumnName.equals("description")) {
                        builder.append("Description: ").append(eventCursor.getString(eventCursor.getColumnIndex("description"))).append("\n");
                    } else if (ColumnName.equals("last_update")) {
                        builder.append("Last Update: ").append(eventCursor.getString(eventCursor.getColumnIndex("last_update"))).append("\n");
                    } else if (ColumnName.equals("discovery_method")) {
                        builder.append("Discovery Method: ").append(eventCursor.getString(eventCursor.getColumnIndex("discovery_method"))).append("\n");
                    } else if (ColumnName.equals("discovery_year")) {
                        builder.append("Discovery Year: ").append(eventCursor.getString(eventCursor.getColumnIndex("discovery_year")));
                    } else {
                        //Log.d("ColumnName Else:", "" + ColumnName);
                    }
                }
            }
            results.add(builder.toString());
            //Log.i("Database Results", "Added to Result");
            eventCursor.moveToNext();
        }
        Log.i("EventBus Cursor ForLoop", "For Loops Done");
        displayResultList();
        eventCursor.close();
    }


    /**
     * This method opens up the app's database, creates a Cursor that queries the SQLite database's Systems table for the information it wants, and outputs that info into an array.
     */
    private void openAndViewDatabase() {
        Cursor mCursor = newDB.rawQuery("SELECT * FROM Planets", null);
        String[] ColumnNames = mCursor.getColumnNames();

        Log.d("Cursor Size", "" + mCursor.getCount());


        mCursor.moveToFirst();
        Log.d("Cursor", "MoveToFirst Called");
        for (int planetRow = 0; planetRow < mCursor.getCount(); planetRow++) {
            Log.d("Cursor Rows", "Rows: " + planetRow);
            StringBuilder builder = new StringBuilder();
            for (String ColumnName : ColumnNames) {
                    Log.d("ColumnName", "" + ColumnName);
                if(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnName)) != null) {
                    if (ColumnName.equals("name")) {
                        builder.append("Name: ").append(mCursor.getString(mCursor.getColumnIndex("name"))).append("\n");
                    } else if (ColumnName.equals("list_type")) {
                        builder.append("List: ").append(mCursor.getString(mCursor.getColumnIndex("list_type"))).append("\n");
                    } else if (ColumnName.equals("mass")) {
                        builder.append("Mass: ").append(mCursor.getString(mCursor.getColumnIndex("mass"))).append("\n");
                    } else if (ColumnName.equals("radius")) {
                        builder.append("Radius: ").append(mCursor.getString(mCursor.getColumnIndex("radius"))).append("\n");
                    } else if (ColumnName.equals("temperature")) {
                        builder.append("Temperature: ").append(mCursor.getString(mCursor.getColumnIndex("temperature"))).append("\n");
                    } else if (ColumnName.equals("period")) {
                        builder.append("Period: ").append(mCursor.getString(mCursor.getColumnIndex("period"))).append("\n");
                    } else if (ColumnName.equals("semi_major_axis")) {
                        builder.append("Semi-Major Axis: ").append(mCursor.getString(mCursor.getColumnIndex("semi_major_axis"))).append("\n");
                    } else if (ColumnName.equals("eccentricity")) {
                        builder.append("Eccentricity: ").append(mCursor.getString(mCursor.getColumnIndex("eccentricity"))).append("\n");
                    } else if (ColumnName.equals("inclination")) {
                        builder.append("Inclination: ").append(mCursor.getString(mCursor.getColumnIndex("inclination"))).append("\n");
                    } else if (ColumnName.equals("periastron")) {
                        builder.append("Periastron: ").append(mCursor.getString(mCursor.getColumnIndex("periastron"))).append("\n");
                    } else if (ColumnName.equals("description")) {
                        builder.append("Description: ").append(mCursor.getString(mCursor.getColumnIndex("description"))).append("\n");
                    } else if (ColumnName.equals("last_update")) {
                        builder.append("Last Update: ").append(mCursor.getString(mCursor.getColumnIndex("last_update"))).append("\n");
                    } else if (ColumnName.equals("discovery_method")) {
                        builder.append("Discovery Method: ").append(mCursor.getString(mCursor.getColumnIndex("discovery_method"))).append("\n");
                    } else if (ColumnName.equals("discovery_year")) {
                        builder.append("Discovery Year: ").append(mCursor.getString(mCursor.getColumnIndex("discovery_year")));
                    } else {
                        //Log.d("ColumnName Else:", "" + ColumnName);
                    }
                }
            }
            results.add(builder.toString());
            //Log.i("Database Results", "Added to Result");
            mCursor.moveToNext();
        }
        //Log.i("Cursor ForLoop", "For Loops Done");
        mCursor.close();
    }


}


