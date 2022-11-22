package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz;

import java.util.ArrayList;
import java.util.List;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;
import lombok.Data;

@Data
public class ListDevicesDTOResponse {
    List<DeviceDTO> devices;

    @Data
    public class DeviceDTO {
        String[] dev_label;
        String did;
        String mac;
        long state_last_timestamp;
        boolean is_online;
        boolean is_low_power;
    }

    public List<Device> toDevicesModel() {
        List<Device> devices = new ArrayList<>();
        List<ListDevicesDTOResponse.DeviceDTO> devicesDTO = getDevices();
        for (ListDevicesDTOResponse.DeviceDTO deviceDTO : devicesDTO) {
            Device device = new Device();
            device.setName(deviceDTO.getDev_label().length > 0 ? deviceDTO.getDev_label()[0] : "");
            if (device.getName().length() == 0)
                device.setName(deviceDTO.getMac());
            device.setGizwitzDeviceId(deviceDTO.getDid());
            device.setMacAddress(deviceDTO.getMac());
            device.setStateLastTimestamp(deviceDTO.getState_last_timestamp());
            device.setIsOnline(deviceDTO.is_online());
            device.setIsLowPower(deviceDTO.is_low_power());
            devices.add(device);
        }
        return devices;
    }
}
