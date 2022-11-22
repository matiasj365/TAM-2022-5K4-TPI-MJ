package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Attr;

import java.util.jar.Attributes;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.R;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.navigation.NavigationDrawerItem;

public class DeviceControlDTORequest {

    /*
    Constantes GitWiz
    0-armar
          bit4-bit7 es el valor de retardo del tiempo de armado
	      0 - sin demora,
	      1 - 16 30 segundos,
	      2 - 32 un minuto,
	      3 - 48 dos minutos
    1-desarmar,
    2-armar en casa,
    3-botón de emergencia;
     */
    public static final int CONTROL_ARM_NOW = 0;
    public static final int CONTROL_DISARM = 1;
    public static final int CONTROL_HOME_ARM = 2;
    public static final int CONTROL_PANIC = 3;

    public static final int CONTROL_ARM_30 = 16;
    public static final int CONTROL_ARM_60 = 32;
    public static final int CONTROL_ARM_120 = 48;


    Attrs attrs = new Attrs();

    public void setControlAction(int controlAction) {
        attrs.APPReteConolState = controlAction;
    }

    class Attrs {
        int GetDeviceStatus;
        int APPReteConolState;
    }
}
