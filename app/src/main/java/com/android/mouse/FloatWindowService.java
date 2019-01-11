package com.android.mouse;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.WindowManager;

public class FloatWindowService extends Service {

    private WindowManager windowManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
