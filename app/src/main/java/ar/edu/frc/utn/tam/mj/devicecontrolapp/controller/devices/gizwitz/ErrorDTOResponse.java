package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz;

import lombok.Data;
/*
{
  "error_message": "username or password error!",
  "error_code": 9020,
  "detail_message": "账号或者密码错误"

}
*/

@Data
public class ErrorDTOResponse {
    public final static int ERROR_CODE_NETWORK_ERROR=1;
    public final static int ERROR_CODE_SERVER_ERROR=2;
    public final static int ERROR_CODE_USER_NOT_EXIST=9005;
    public final static int ERROR_CODE_INVALID_CREDENTIALS=9020;
    private String error_message;
    private int error_code;
    private String detail_message;
}
