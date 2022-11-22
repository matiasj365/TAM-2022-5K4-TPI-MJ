package ar.edu.frc.utn.tam.mj.devicecontrolapp.view.navigation;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.DevicesController;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.Result;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceHandlerException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.User;

public class NavigationViewModel extends ViewModel {
    private MutableLiveData<Result<List<Device>>> devices = new MutableLiveData<>();
    private DevicesController devicesController;
    public NavigationViewModel(DevicesController devicesController) {

        super();
        this.devicesController = devicesController;
    }

    public MutableLiveData<Result<List<Device>>> getDevices() {
        return devices;
    }

    public void setDevices(List<Device>devices) {
        Result.Success success= new Result.Success<List<Device>>(devices);
        this.devices.setValue(success);
    }

    public void setError(DeviceHandlerException exception) {
    }

    public void listDevices(Application app, int serviceClass) throws DeviceHandlerException {
        devicesController.listDevices(this,app,serviceClass);
    }
}
