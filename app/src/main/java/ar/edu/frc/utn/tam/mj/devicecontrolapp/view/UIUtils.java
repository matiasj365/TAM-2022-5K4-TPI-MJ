package ar.edu.frc.utn.tam.mj.devicecontrolapp.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.text.StringKt;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.R;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.ErrorDTOResponse;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceControlAppException;

public class UIUtils {

    public static void sendNotification(Context context, String message)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), UIConstants.NOTIFICATIONS_CHANNEL)
                .setSmallIcon(androidx.constraintlayout.widget.R.drawable.notification_template_icon_bg)
                .setContentTitle("Mensaje de Alarma")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(Long.valueOf(System.currentTimeMillis()).intValue(), builder.build());
    }



    public static int getErrorMessage(DeviceControlAppException ex) {
        switch (ex.getErrorCode()) {

            case ErrorDTOResponse.ERROR_CODE_USER_NOT_EXIST:
            case ErrorDTOResponse.ERROR_CODE_INVALID_CREDENTIALS:
                return R.string.login_invalid_credentials;
            case ErrorDTOResponse.ERROR_CODE_NETWORK_ERROR:
                return R.string.network_timeot;
            default:
                return R.string.unknown_error;
        }
    }

    private static SharedPreferences sharedPreferences;

    public static void updateToolbar(String newTittle)
    {

    }
    public static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null)
            loadSharedPreferences(context);
        return sharedPreferences;
    }

    private static void loadSharedPreferences(Context context) {
        KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
        String mainKeyAlias = null;
        try {
            mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
            String sharedPrefsFile = "DeviceControlApp.settings";
            sharedPreferences = EncryptedSharedPreferences.create(
                    sharedPrefsFile,
                    mainKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException ex) {
             new DeviceControlAppException(UIUtils.class, -1, ex);
        }
    }
}
