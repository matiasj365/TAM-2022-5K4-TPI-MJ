package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices;

import android.app.Application;

import java.util.HashMap;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.ListDevicesDTORequest;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.UserDTORequest;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceHandlerException;

public interface DeviceHandler {


    void init(HashMap<String, Object> params) throws DeviceHandlerException;

    void arm(String deviceId,long phoneNumber, int armTime) throws DeviceHandlerException;

    void disarm(String deviceId,long phoneNumber) throws DeviceHandlerException;

    void homeArm(String deviceId,long phoneNumber) throws DeviceHandlerException;

    void panic(String deviceId,long phoneNumber) throws DeviceHandlerException;

    void login(UserDTORequest userDTORequest) throws DeviceHandlerException;

    void listDevices(ListDevicesDTORequest requestDTO, Application app) throws DeviceHandlerException;

    DeviceHandler getInstance(HashMap<String, Object> handlerParams);

    void setResponseLoginListener(ResponseLoginListener listener);

    void setResponseListDevicesListener(ResponseListDevicesListener listener);
}
