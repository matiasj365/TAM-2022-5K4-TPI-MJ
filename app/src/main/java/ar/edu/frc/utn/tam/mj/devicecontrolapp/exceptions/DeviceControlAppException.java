package ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions;

import android.util.Log;

public class DeviceControlAppException extends Exception{

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    private int errorCode;
    public DeviceControlAppException(Class tag, int errorCode, Throwable cause) {
        super(cause.getMessage()!=null?cause.getMessage():"", cause);
        this.errorCode=errorCode;
        String message=cause.getMessage()!=null?cause.getMessage():"Null Message";

        Log.e(tag.getName(),message);
        Log.v(tag.getName(),message,cause);
    }
    public DeviceControlAppException(Class tag, int errorCode, String message) {
        super(message);
        this.errorCode=errorCode;
        message=message!=null?message:" Null Message";
        Log.e(tag.getName(),message);
        Log.v(tag.getName(),message);
    }
    public DeviceControlAppException(Class tag, int errorCode, String message,Throwable cause) {
        super(cause.getMessage()!=null?cause.getMessage():"", cause);
        this.errorCode=errorCode;
        message=message!=null?message:" Null Message";
        Log.e(tag.getName(),message);
        Log.v(tag.getName(),message);
    }
}
