package ar.edu.frc.utn.tam.mj.devicecontrolapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.VolleyHelper;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceConstants;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceHandler;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceHandlerFactory;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.sms.DeviceSmsReceiver;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceControlAppException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.User;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.UIConstants;

public class DeviceControllApplication extends Application {
    User loggedUser;
    private DeviceSmsReceiver receiver = new DeviceSmsReceiver();
    @Override
    public void onCreate() {
        try {
            super.onCreate();
            VolleyHelper.getInstance(this);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            createNotificationChannel();

            initHandlers();
        } catch (DeviceControlAppException e) {
            e.printStackTrace();
        }

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(UIConstants.NOTIFICATIONS_CHANNEL, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void initHandlers() throws DeviceControlAppException {
        DeviceHandler handlerApi = DeviceHandlerFactory.getInstance(DeviceConstants.CLASS_SERVICE_API_HANDLER);
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
