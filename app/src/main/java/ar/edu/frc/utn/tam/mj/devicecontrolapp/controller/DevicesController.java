package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceConstants;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceHandler;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceHandlerFactory;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.ResponseListDevicesListener;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.ListDevicesDTORequest;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceControlAppException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceHandlerException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.User;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.navigation.NavigationViewModel;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class DevicesController implements ResponseListDevicesListener {

    private final static DevicesController instance = new DevicesController();
    ;

    private DevicesController() {
    }

    private static Map<Integer, DeviceHandler> deviceHandlers;


    public static DevicesController getInstance() throws DeviceControlAppException {
        if (deviceHandlers == null) {
            deviceHandlers = new HashMap<>();
            deviceHandlers.put(DeviceConstants.SERVICE_MODE_API,
                    DeviceHandlerFactory.getInstance(DeviceConstants.CLASS_SERVICE_API_HANDLER));
            deviceHandlers.put(DeviceConstants.SERVICE_MODE_SMS,
                    DeviceHandlerFactory.getInstance(DeviceConstants.CLASS_SERVICE_SMS_HANDLER));
            deviceHandlers.put(DeviceConstants.SERVICE_MODE_DUMMY,
                    DeviceHandlerFactory.getInstance(DeviceConstants.CLASS_SERVICE_DUMMY_HANDLER));
        }
        return instance;
    }

    User user;
    NavigationViewModel viewModel;

    public void listDevices(NavigationViewModel viewModel, Application app, int serviceClass) throws DeviceHandlerException {
        this.viewModel = viewModel;
            deviceHandlers.get(serviceClass).setResponseListDevicesListener(this);
            deviceHandlers.get(serviceClass).listDevices(new ListDevicesDTORequest(), app);
    }

    private ResponseListDevicesListener listener;

    private List<Device> listDummyDevices() {
        List<Device> deviceList = new ArrayList<>();
        Device device = new Device();
        device.setDeviceId(1);
        device.setName("Simulador 1");
        device.setServiceClass(DeviceConstants.SERVICE_MODE_DUMMY);
        device.setSmsPhoneNumber(3513569672l);
        device.setMacAddress("00ffffffff00");
        deviceList.add(device);

        device = new Device();
        device.setDeviceId(2);
        device.setName("Simulador 2");
        device.setServiceClass(DeviceConstants.SERVICE_MODE_DUMMY);
        device.setSmsPhoneNumber(3513569672l);
        device.setMacAddress("00ffffffff00");
        deviceList.add(device);
        return deviceList;
    }

    @Override
    public void onListDevicesSuccess(List<Device> devices) {
        viewModel.setDevices(devices);

    }

    @Override
    public void onError(DeviceHandlerException exception) {
        viewModel.setError(exception);
    }
}