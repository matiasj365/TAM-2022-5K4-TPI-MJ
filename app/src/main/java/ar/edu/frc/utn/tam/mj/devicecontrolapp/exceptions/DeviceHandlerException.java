package ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions;

public class DeviceHandlerException extends DeviceControlAppException {
    public DeviceHandlerException(Class tag, int errorCode, Throwable cause) {
        super(tag, errorCode, cause);
    }

    public DeviceHandlerException(Class tag, int errorCode, String message) {
        super(tag, errorCode ,message);
    }
    public DeviceHandlerException(Class tag, int errorCode, String message,Throwable cause) {
        super(tag, errorCode ,message,cause);
    }
}
