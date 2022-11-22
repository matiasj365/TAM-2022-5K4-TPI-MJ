package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.DeviceEvent;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence.AppDatabase;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence.DeviceDao;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence.DeviceEventDao;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.UIConstants;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.UIUtils;

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
                    String messageFrom = smsMessage.getOriginatingAddress().substring(3);

                    long phoneNumber = Long.valueOf(messageFrom);
                    DeviceDao deviceDao = AppDatabase.getDatabase(context.getApplicationContext()).deviceDao();
                    Device device = deviceDao.findBySmsPhoneNumber(phoneNumber);
                    String notification=messageFrom + " - ";
                    if(device!=null)
                    {
                        notification+=device.getName()+ " - ";
                        updateDeviceEvents(context,device,smsMessage);
                    }
                    UIUtils.sendNotification(context, notification + messageBody);
                }
            }
        }
    }

    private void updateDeviceEvents(Context context,Device device, SmsMessage message) {
        DeviceEvent deviceEvent=new DeviceEvent();
        deviceEvent.setDeviceId(device.getDeviceId());
        deviceEvent.setTimestamp(System.currentTimeMillis());
        deviceEvent.setMessage(message.getMessageBody());
        DeviceEventDao dao=AppDatabase.getDatabase(context.getApplicationContext()).deviceEventDao();
        dao.insert(deviceEvent);

    }
}
