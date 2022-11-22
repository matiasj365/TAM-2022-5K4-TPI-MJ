package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices;

import androidx.lifecycle.LiveData;

import java.util.List;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.ListDevicesDTOResponse;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.UserDTOResponse;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceHandlerException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.navigation.NavigationViewModel;

public interface ResponseListDevicesListener {
    void onListDevicesSuccess(List<Device>devices);
    void onError(DeviceHandlerException ex);
}
