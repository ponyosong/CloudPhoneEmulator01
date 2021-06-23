package com.haxqer.cloudphone01useremulator01;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibility";
    private static final String PackageName = "com.ss.android.ugc.aweme";
    private static Timer timer = new Timer();
    private final ToastHandler hHandler = new ToastHandler();

    private void startService()
    {
        timer.scheduleAtFixedRate(new mainTask(), 0, 5000);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            hHandler.sendEmptyMessage(0);
        }
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected");
        openTiktok();
        startService();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent");

        doTiktokLogical(event);
    }

    private void doTiktokLogical(AccessibilityEvent event) {
        try {
            if (event.getPackageName() == null) {
                Log.d(TAG, "onAccessibilityEvent: package name is null, return");
                return;
            }

            if (!event.getPackageName().equals(PackageName)){
                Log.d(TAG, "event.getPackageName is" + event.getPackageName());
                return;
            }
            AccessibilityNodeInfo source = event.getSource();
            Log.d(TAG, source.toString());
//            AccessibilityNodeInfo root = getRootInActiveWindow();
//            Log.d(TAG, root.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public void openTiktok() {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(PackageName);
        startActivity(launchIntent);
    }

    class ToastHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
//            Toast.makeText(getApplicationContext(), getApplicationContext().getPackageName(), Toast.LENGTH_SHORT).show();

            try {
                AccessibilityNodeInfo root = getRootInActiveWindow();
                CharSequence packageName = root.getPackageName();
                if (packageName == null) {
                    Log.d(TAG, "Handler package name is null, return");
                    return;
                }

                if (!packageName.equals(PackageName)){
                    Log.d(TAG, "packageName is" + packageName);
                    return;
                }

//            NodesInfo.show(event.getSource(), TAG, "d");
//                Log.d(TAG, root.toString());
//                root.performAction();
                scroll01(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void scroll01(AccessibilityNodeInfo group) {
        Log.d(TAG, "scroll01");
        if (group != null) {
            List<AccessibilityNodeInfo> list = group.findAccessibilityNodeInfosByViewId("com.ss.android.ugc.aweme:id/viewpager");
//            List<AccessibilityNodeInfo> list = group.findAccessibilityNodeInfosByViewId("com.ss.android.ugc.aweme:id/br2");
            for (AccessibilityNodeInfo item : list) {
                Log.d(TAG, item.toString());
//                if (item.isClickable()) {
//                    Log.d(TAG, item.toString());
//                    item.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
//                }

                if (item.isClickable()) {
                    if (item.isScrollable()) {
                        Log.d(TAG, item.toString());
                        item.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                    }
                }
            }



//            Log.d(TAG, list.get(0).toString());
//            list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }


    }

}
