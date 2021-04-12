package ua.opu.announcement;

public interface ReceiversCallback {
    void onAirplaneModeChange(boolean isTurnedOn);
    void onBatteryLow();
    void onOpenCamera();
}
