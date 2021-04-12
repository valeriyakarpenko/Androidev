package ua.opu.announcement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ReceiversCallback {

    AirplaneModeReceiver airplaneModeReceiver;
    LowBatteryReceiver lowBatteryReceiver;
    CameraOnReceiver cameraOnReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        airplaneModeReceiver = new AirplaneModeReceiver();
        lowBatteryReceiver = new LowBatteryReceiver();
        cameraOnReceiver = new CameraOnReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeReceiver, filter);

        filter.addAction(Intent.ACTION_BATTERY_LOW);
        registerReceiver(lowBatteryReceiver, filter);

        filter.addAction(Intent.ACTION_CAMERA_BUTTON);
        registerReceiver(cameraOnReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (airplaneModeReceiver != null)
            unregisterReceiver(airplaneModeReceiver);
        if (lowBatteryReceiver != null)
            unregisterReceiver(lowBatteryReceiver);
        if (cameraOnReceiver != null)
            unregisterReceiver(cameraOnReceiver);
    }

    @Override
    public void onAirplaneModeChange(boolean isTurnedOn) {
        String message = isTurnedOn ? "Airplane mode is turned on!" : "Airplane mode is turned off!";
        AnnouncementDialog.newInstance("Airplane", message).show(getSupportFragmentManager(), "dlg");
    }

    @Override
    public void onBatteryLow() {
        AnnouncementDialog.newInstance("Low battery", "Battery is too low!").show(getSupportFragmentManager(), "dlg");
    }

    @Override
    public void onOpenCamera() {
        AnnouncementDialog.newInstance("Opening camera", "Opening camera!").show(getSupportFragmentManager(), "dlg");
    }
}