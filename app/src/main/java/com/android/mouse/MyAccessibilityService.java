package com.android.mouse;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import permission.FloatWindowManager;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        Toast.makeText(getApplicationContext(), "onServiceConnected", Toast.LENGTH_SHORT).show();
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Toast.makeText(getApplicationContext(), "onAccessibilityEvent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(getApplicationContext(), "onInterrupt", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        Log.i("onKeyDown", event.toString()+"["+event.getKeyCode()+"]");
        int DefaultSpeed = 25;
        int leftright = 0;
        int updown = 0;
        switch (event.getKeyCode()){
            case 96:
                Toast.makeText(getApplicationContext(), "Btn[A]", Toast.LENGTH_SHORT).show();
                break;
            case 97:
                Toast.makeText(getApplicationContext(), "Btn[B]", Toast.LENGTH_SHORT).show();
                break;
            case 19:
                Toast.makeText(getApplicationContext(), "Btn[up]", Toast.LENGTH_SHORT).show();
                updown+=DefaultSpeed;
                break;
            case 20:
                Toast.makeText(getApplicationContext(), "Btn[down]", Toast.LENGTH_SHORT).show();
                updown-=DefaultSpeed;
                break;
            case 21:
                Toast.makeText(getApplicationContext(), "Btn[left]", Toast.LENGTH_SHORT).show();
                leftright+=DefaultSpeed;
                break;
            case 22:
                Toast.makeText(getApplicationContext(), "Btn[right]", Toast.LENGTH_SHORT).show();
                leftright-=DefaultSpeed;
                break;
            default:
                return super.onKeyEvent(event);
        }
        // update float window
        FloatWindowManager.getInstance().UpdateFloatWindow(leftright, updown);
        return true;
    }
}
