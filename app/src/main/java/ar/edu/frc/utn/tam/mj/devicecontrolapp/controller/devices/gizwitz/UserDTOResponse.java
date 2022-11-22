package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz;

import lombok.Data;
@Data
public class UserDTOResponse {
    private String token;
    private String uid;
    private long expire_at;
}
