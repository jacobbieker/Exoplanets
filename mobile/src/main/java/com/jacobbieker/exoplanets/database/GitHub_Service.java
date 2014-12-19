package com.jacobbieker.exoplanets.database;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GitHub_Service extends Service {
    public GitHub_Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }


}
