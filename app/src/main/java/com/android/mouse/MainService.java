package com.android.mouse;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import permission.FloatWindowManager;

public class MainService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "service bind", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(getApplicationContext(), "service create", Toast.LENGTH_SHORT).show();
        FloatWindowManager.getInstance().showWindow(MainService.this);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "service destroy", Toast.LENGTH_SHORT).show();
        FloatWindowManager.getInstance().dismissWindow();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "service start command", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }
}
