package com.haxqer.cloudphone01useremulator01;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    Context context = getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private void startService() {
        if (!isServiceEnabled()) {
            Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(accessibleIntent);
        }
    }

    private void displayText() {
        Toast.makeText(context, "click me", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "click me");
    }

    private boolean isServiceEnabled() {
        AccessibilityManager accessibilityManager =    (AccessibilityManager)getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> accessibilityServices =
                accessibilityManager.getEnabledAccessibilityServiceList(
                        AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().contains("com.haxqer.cloudphone01useremulator01")) {
                return true;
            }
        }
        return false;
    }
}