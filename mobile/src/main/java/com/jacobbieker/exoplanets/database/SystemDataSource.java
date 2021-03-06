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

package com.jacobbieker.exoplanets.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jacobbieker.exoplanets.beans.System;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob on 3/3/14.
 */
public class SystemDataSource {

    //Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { DatabaseHelper.SYSTEM_COLUMN_ID, DatabaseHelper.SYSTEM_COLUMN_NAME, DatabaseHelper.SYSTEM_COLUMN_DECLINATION, DatabaseHelper.SYSTEM_COLUMN_RIGHTASCENSION, DatabaseHelper.SYSTEM_COLUMN_DISTANCE, DatabaseHelper.SYSTEM_COLUMN_EPOCH };

    public SystemDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     *
     * @param system A System object
     * @return A Cursor that inserts a new entry into the database
     */
    public System createSystem(String system) {
        Log.i("Database", "Create System Called");
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SYSTEM_COLUMN_NAME, system);
        long insertId = database.insert(DatabaseHelper.TABLE_SYSTEMS_NAME, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_SYSTEMS_NAME, allColumns, DatabaseHelper.SYSTEM_COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        System newSystem = cursorToSystem(cursor);
        cursor.close();
        Log.i("Database", "Create System Succeeded");
        return newSystem;
    }

    /**
     *
     * @param system A System object
     */
    public void deleteSystem(System system) {
        Log.i("Database", "Delete System Called");
        long id = system.getId();
        java.lang.System.out.println("System deleted with id: " + id);
        database.delete(DatabaseHelper.TABLE_SYSTEMS_NAME, DatabaseHelper.SYSTEM_COLUMN_ID + " = " + id, null);
        Log.i("Database", "Delete System Succeeded");
    }

    public List<System> getAllSystems() {
        Log.i("Database", "Get All Systems Called");
        List<System> systems = new ArrayList<System>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_SYSTEMS_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            System system = cursorToSystem(cursor);
            systems.add(system);
            cursor.moveToNext();
        }
        //make sure to close the cursor
        cursor.close();
        Log.i("Database", "Get All Systems Succeeded");
        return systems;
    }


    /**
     *
     * @param cursor a Cursor containing System objects
     * @return A System object
     */
    private System cursorToSystem(Cursor cursor) {
        Log.i("Cursor", "cursorToSystem called");
        System system = new System();
        system.setId(cursor.getLong(0));
        system.setSystem(cursor.getString(1));
        return system;
    }

}
