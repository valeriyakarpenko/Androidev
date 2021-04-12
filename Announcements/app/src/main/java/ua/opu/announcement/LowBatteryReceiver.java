package ua.opu.announcement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LowBatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
            if (context instanceof ReceiversCallback) {
                ((ReceiversCallback) context).onBatteryLow();
            }
        }
    }
}
