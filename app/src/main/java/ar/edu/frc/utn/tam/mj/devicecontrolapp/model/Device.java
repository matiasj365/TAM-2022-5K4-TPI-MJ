package ar.edu.frc.utn.tam.mj.devicecontrolapp.model;

import androidx.room.Ignore;

import java.io.Serializable;
import java.util.ArrayList;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceConstants;
import lombok.Data;

@androidx.room.Entity
@Data
public class Device implements Serializable {
    @androidx.room.PrimaryKey(autoGenerate = true)
    Integer deviceId;
    String gizwitzDeviceId;
    String name;
    Long stateLastTimestamp;
    Boolean isOnline;
    String macAddress;
    Long smsPhoneNumber;
    Boolean isLowPower;
    int serviceClass;
    boolean allowSmsNotifications;
    boolean allowApiNotifications;

public boolean isServiceModeAllowed(int serviceMode)
    {
        if(macAddress==null && smsPhoneNumber==null && serviceMode!= DeviceConstants.SERVICE_MODE_DUMMY)
            return false;
        if(smsPhoneNumber!=null && serviceMode==DeviceConstants.SERVICE_MODE_SMS)
            return false;
        if(gizwitzDeviceId==null && serviceMode==DeviceConstants.SERVICE_MODE_API)
            return false;
        return true;
    }
}
