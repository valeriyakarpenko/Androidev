package ua.opu.announcement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CameraOnReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_CAMERA_BUTTON)) {
            if (context instanceof ReceiversCallback) {
                ((ReceiversCallback) context).onOpenCamera();
            }
        }
    }
}
