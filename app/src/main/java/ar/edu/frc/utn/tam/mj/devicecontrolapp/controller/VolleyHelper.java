package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyHelper {
    private static VolleyHelper instance;
    private static RequestQueue queue;

    private VolleyHelper(Context context){
        queue = Volley.newRequestQueue(context);
    }
    //private static RequestQueue queue;
    public static VolleyHelper getInstance(Context context) {
        if(instance==null)
            instance=new VolleyHelper(context);
        return instance;
    }
    public static RequestQueue getSharedQueue() {
        return queue;
    }

}
