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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jacobbieker.exoplanets.database.DatabaseHelper;
import com.jacobbieker.exoplanets.database.SystemDataSource;
import com.jacobbieker.exoplanets.eventbus.ItemSelected;
import com.jacobbieker.exoplanets.eventbus.ItemSelectedEvent;
import com.jacobbieker.exoplanets.R;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Bieker on 12/5/13.
 */
public class SystemFragment extends ListFragment implements OnItemClickListener  {

    private ArrayList<String> results = new ArrayList<String>();
    private String tablePlanetsName = "planets";
    private SQLiteDatabase newDB;

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
       View view = inflater.inflate(R.layout.star_list_fragment, container, false);
        Log.i("Fragment", "OnCreateView(): View Created");
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
        // TODO Auto-generated method stub
        String item = adapter.getItemAtPosition(position).toString();
        Log.i("OnItemClick", "String Item found:" + position);
        Toast.makeText(getActivity().getApplicationContext(), "Click Item Position: " + item, Toast.LENGTH_LONG);
        Log.i("OnItemClick", "Toast Called: " + item);
        EventBus.getDefault().post(new ItemSelectedEvent(new ItemSelected(arg3)));

    }

    public void onEvent(ItemSelectedEvent event) {
       Log.i("EventBus SystemFrag event", " ItemSelectedEvent called");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        databaseHelper = new DatabaseHelper(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newDB = databaseHelper.getWritableDatabase();
        //Log.i("SystemFragment", "openAndViewDatabase called");
        openAndViewDatabase();
        //Log.i("SystemFragment", "displayResultList Called");
        displayResultList();
        ListView listView = (ListView)getListView().findViewById(android.R.id.list);
        listView.setOnItemClickListener(this);
        //Log.i("ListView", "setOnItemCLickListener called and set");
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * This method takes the output from openAndViewDatabase() and inserts it into a ListView that is displayed to the user using an ArrayAdapter
     */
    private void displayResultList() {
            Log.i("displayResultList", "setListAdapterCalled");
            setListAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item, results));
        }


    /**
     * This method opens up the app's database, creates a Cursor that queries the SQLite database's Systems table for the information it wants, and outputs that info into an array.
     */
    private void openAndViewDatabase() {
            Cursor mCursor = newDB.rawQuery("SELECT * FROM Systems", null);
            String[] ColumnNames = mCursor.getColumnNames();

            Log.i("Cursor Size", "" + mCursor.getCount()); // It Works Returns Size of The  Table


            mCursor.moveToFirst();
            Log.i("Cursor", "MoveToFirst Called");
            for (int row = 0; row < mCursor.getCount(); row++) {
                //Log.i("Cursor Rows", "Rows: " + row);
                StringBuilder builder = new StringBuilder();
                for (String ColumnName : ColumnNames) {

                    //Log.d("ColumnName", "" + ColumnName); // It Works Returns The Name Of The Column
                    //Log.d("Column Value", mCursor.getString(mCursor.getColumnIndex(ColumnName)));

                    if (ColumnName.equals("name")) {
                        builder.append("Name: ").append(mCursor.getString(mCursor.getColumnIndex("name"))).append("\n");
                    } else if (ColumnName.equals("rightAscension")) {
                        builder.append("Right Ascension: ").append(mCursor.getString(mCursor.getColumnIndex("rightAscension"))).append("\n");
                    } else if (ColumnName.equals("declination")) {
                        builder.append("Declination: ").append(mCursor.getString(mCursor.getColumnIndex("declination"))).append("\n");
                    } else if (ColumnName.equals("distance")) {
                        builder.append("Distance: ").append(mCursor.getString(mCursor.getColumnIndex("distance")));
                    } else {
                        Log.d("ColumnName Else:", "" + ColumnName);
                    }

                }
                results.add(builder.toString());
                //Log.i("Database Results", "Added to Result");
                mCursor.moveToNext();
            }
        Log.i("Cursor ForLoop", "For Loops Done");
        mCursor.close();
    }


    }
