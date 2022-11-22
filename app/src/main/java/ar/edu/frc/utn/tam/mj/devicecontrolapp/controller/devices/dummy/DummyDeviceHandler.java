package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.dummy;

import android.app.Application;

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
public class DummyDeviceHandler implements DeviceHandler {

    private static DeviceHandler instance;

    HashMap<String, String> headers = new HashMap<>();
    private String host;
    private ResponseListDevicesListener responseListDevicesListener;

    @Override
    public void init(HashMap<String, Object> params) throws DeviceHandlerException {
    }

    @Override
    public void arm(String deviceId, long phoneNumber, int armTime) throws DeviceHandlerException {

    }

    @Override
    public void disarm(String deviceId, long phoneNumber) throws DeviceHandlerException {

    }

    @Override
    public void homeArm(String deviceId, long phoneNumber) throws DeviceHandlerException {

    }

    @Override
    public void panic(String deviceId, long phoneNumber) throws DeviceHandlerException {

    }

    @Override
    public void login(UserDTORequest userDTORequest) throws DeviceHandlerException {

    }

    @Override
    public void listDevices(ListDevicesDTORequest requestDTO, Application app) throws DeviceHandlerException {

    }

    @Override
    public DeviceHandler getInstance(HashMap<String, Object> handlerParams) {
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
