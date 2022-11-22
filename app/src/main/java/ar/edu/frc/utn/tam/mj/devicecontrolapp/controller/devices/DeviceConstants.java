package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.dummy.DummyDeviceHandler;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.GizwitzDeviceAPIHandler;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.sms.SMSDeviceHandler;

public class DeviceConstants {
    public final static int SERVICE_MODE_API = 1;
    public final static int SERVICE_MODE_SMS = 2;
    public final static int SERVICE_MODE_DUMMY = 3;

    public final static int EVENT_UNKNOWN = 0;
    public final static int EVENT_ARMED = 1;
    public final static int EVENT_DISARMED=2;
    public final static int EVENT_HOME_ARMED=3;
    public final static int EVENT_ALARMING=4;
    public final static int EVENT_LOW_BATTERY=5;
    public final static int EVENT_ON_LINE=6;
    public final static int EVENT_OFF_LINE=7;
    public final static int EVENT_SENSOR_LOW_BATTERY=8;

    public static String CLASS_SERVICE_API_HANDLER = GizwitzDeviceAPIHandler.class.getName();
    public static String CLASS_SERVICE_SMS_HANDLER = SMSDeviceHandler.class.getName();
    public static String CLASS_SERVICE_DUMMY_HANDLER = DummyDeviceHandler.class.getName();
}
