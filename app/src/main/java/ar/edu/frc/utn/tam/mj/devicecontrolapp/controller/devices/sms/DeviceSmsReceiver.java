package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.UIConstants;

public class DeviceSmsReceiver extends BroadcastReceiver {
    private static final String TAG =
            DeviceSmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("", "Receiv");
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    String messageBody = smsMessage.getMessageBody();
                    sendNotification(context, messageBody);
                }
            }
        }
    }

    public void sendNotification(Context context, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), UIConstants.NOTIFICATIONS_CHANNEL)
                .setSmallIcon(androidx.constraintlayout.widget.R.drawable.notification_template_icon_bg)
                .setContentTitle("Mensaje de Alarma")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(Long.valueOf(System.currentTimeMillis()).intValue(), builder.build());
    }
}
