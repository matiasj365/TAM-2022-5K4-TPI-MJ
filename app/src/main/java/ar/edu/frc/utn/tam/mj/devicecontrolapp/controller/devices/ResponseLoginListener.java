package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.UserDTOResponse;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceHandlerException;

public interface ResponseLoginListener {
    void onLoginSuccess(UserDTOResponse user);
    void onError(DeviceHandlerException ex);
}
