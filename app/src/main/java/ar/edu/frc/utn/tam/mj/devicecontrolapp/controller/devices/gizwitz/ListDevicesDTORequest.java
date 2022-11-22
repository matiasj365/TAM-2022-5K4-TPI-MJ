package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz;

import lombok.Data;

@Data
public class ListDevicesDTORequest {
    private int show_disabled=1;
    private int limit=20;
    private int skip=0;
}
