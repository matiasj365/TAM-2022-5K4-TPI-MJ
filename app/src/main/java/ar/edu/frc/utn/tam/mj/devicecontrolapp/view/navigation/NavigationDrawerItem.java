package ar.edu.frc.utn.tam.mj.devicecontrolapp.view.navigation;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;
import lombok.Data;

@Data
public class NavigationDrawerItem {

    private Device device;
    private int deviceId;
    private String deviceName;
    private int smsImageId;
    private int apiImageId;
    private int connectionStatusIcon;
    private int powerStatusIcon;

    public NavigationDrawerItem(Device device) {
        setDevice(device);
    }

    public void setDevice(Device device) {
        connectionStatusIcon=android.R.drawable.ic_menu_close_clear_cancel;
        powerStatusIcon=android.R.drawable.ic_menu_close_clear_cancel;
        smsImageId = android.R.drawable.ic_menu_close_clear_cancel;
        apiImageId = android.R.drawable.ic_menu_close_clear_cancel;
        this.device = device;
        deviceId = device.getDeviceId();
        setDeviceName(device.getName());
        if (device.getMacAddress() != null && device.getMacAddress().length() > 0) {
            apiImageId = android.R.drawable.ic_secure;
            if(device.getIsOnline()!=null && device.getIsOnline()) {
                connectionStatusIcon = android.R.drawable.ic_menu_compass;
                if(device.getIsLowPower()!=null && !device.getIsLowPower())
                    powerStatusIcon=android.R.drawable.ic_lock_idle_charging;
                else
                    powerStatusIcon=android.R.drawable.ic_lock_idle_low_battery;
            }
            else {
                connectionStatusIcon = android.R.drawable.ic_delete;
                powerStatusIcon=android.R.drawable.ic_lock_idle_low_battery;
            }
        }
        if (device.getSmsPhoneNumber() != null)
            smsImageId = android.R.drawable.ic_dialog_email;
        if (device.getMacAddress() == null && device.getSmsPhoneNumber() == null)
            apiImageId = android.R.drawable.ic_menu_delete;

    }
}