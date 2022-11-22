package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.sms;

import android.app.Application;
import android.telephony.SmsManager;

import androidx.lifecycle.LiveData;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceHandler;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.ResponseListDevicesListener;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.ResponseLoginListener;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.ListDevicesDTORequest;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.UserDTORequest;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceHandlerException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence.AppRepository;
import lombok.Data;

@Data
public class SMSDeviceHandler implements DeviceHandler {

    private static DeviceHandler instance;

    HashMap<String, String> headers = new HashMap<>();
    private String host;
    private ResponseListDevicesListener responseListDevicesListener;

    @Override
    public void init(HashMap<String, Object> params) throws DeviceHandlerException {
    }

    private void controlDevice(String deviceId, int controlAction) throws JSONException {
    }

    @Override
    public void arm(String deviceId, long phoneNumber, int armTime) throws DeviceHandlerException {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(String.valueOf(phoneNumber), null, "8412#1", null, null);
    }

    @Override
    public void disarm(String deviceId,long phoneNumber) throws DeviceHandlerException {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(String.valueOf(phoneNumber), null, "8412#2", null, null);
    }

    @Override
    public void homeArm(String deviceId,long phoneNumber) throws DeviceHandlerException {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(String.valueOf(phoneNumber), null, "8412#2", null, null);
    }

    @Override
    public void panic(String deviceId,long phoneNumber) throws DeviceHandlerException {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(String.valueOf(phoneNumber), null, "8412#4", null, null);
    }

    @Override
    public void login(UserDTORequest userDTORequest) {
    }

    @Override
    public void listDevices(ListDevicesDTORequest requestDTO, Application app) throws DeviceHandlerException {
        LiveData<List<Device>> smsDevices = AppRepository.getInstance(app).getSmsDevices();
        responseListDevicesListener.onListDevicesSuccess(smsDevices.getValue());
    }


    @Override
    public DeviceHandler getInstance(HashMap<String, Object> handlerParams) {
        if (instance != null)
            return instance;
        instance = new SMSDeviceHandler();
        try {

        } catch (Exception ex) {

        }
        return null;
    }

    @Override
    public void setResponseLoginListener(ResponseLoginListener listener) {

    }

    @Override
    public void setResponseListDevicesListener(ResponseListDevicesListener listener) {
        this.responseListDevicesListener=listener;

    }
}
