package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz;

import lombok.Data;

/*
{
  "error_message": "user does not exist!",
  "error_code": 9005,
  "detail_message": null
}
 */
@Data
public class UserDTOErrorResponse {
    private String error_message;
    private String error_code;
    private String detail_message;

}
