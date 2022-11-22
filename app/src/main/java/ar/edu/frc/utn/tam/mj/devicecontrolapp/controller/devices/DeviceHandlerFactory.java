package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices;

import android.util.Log;

import java.util.HashMap;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.dummy.DummyDeviceHandler;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.GizwitzDeviceAPIHandler;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.sms.SMSDeviceHandler;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceControlAppException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceHandlerException;

public class DeviceHandlerFactory {
    private static HashMap<String, DeviceHandler> instances = new HashMap<>();

    public static DeviceHandler getInstance(String deviceServiceMode) throws DeviceControlAppException {
        try {
            if (!instances.containsKey(deviceServiceMode)) {
                Class serviceClass = Class.forName(deviceServiceMode);
                DeviceHandler instance = (DeviceHandler) serviceClass.newInstance();
                instance.init(null);
                instances.put(deviceServiceMode, instance);
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException exception) {
            throw new DeviceHandlerException(DeviceHandlerFactory.class,-1, exception);
        }
        return instances.get(deviceServiceMode);
    }
}
