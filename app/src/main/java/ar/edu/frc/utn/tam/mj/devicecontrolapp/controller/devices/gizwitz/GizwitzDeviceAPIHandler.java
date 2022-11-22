package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz;

import android.app.Application;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.VolleyCallback;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.VolleyHelper;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceHandler;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.ResponseListDevicesListener;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.ResponseLoginListener;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceHandlerException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence.AppDatabase;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence.AppRepository;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence.DeviceDao;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class GizwitzDeviceAPIHandler implements DeviceHandler {

    public final static String PARAMS_USERNAME = "username";
    public final static String PARAMS_PASSWORD = "password";
    public final static String POST_URL_LOGIN = "/login";
    public final static String GET_URL_LIST_DEVICES = "/bindings";
    public final static String POST_URL_CONTROL_DEVICE = "/control/";
    private static DeviceHandler instance;
    ResponseLoginListener responseLoginListener;
    ResponseListDevicesListener responseListDevicesListener;

    HashMap<String, String> headers = new HashMap<>();
    private String host;

    @Override
    public void init(HashMap<String, Object> params) throws DeviceHandlerException {
//        host = Keys.INSTANCE.hostURL();
//        headers.put("x-gizwits-application-id", Keys.INSTANCE.applicationId());
        host ="https://usapi.gizwits.com/app";
        headers.put("x-gizwits-application-id", "84beb08d919742c9b766f4f57f9c6465");

    }

    private void controlDevice(String deviceId, int controlAction) throws JSONException {
        Gson gson = new Gson();
        DeviceControlDTORequest requestDTO = new DeviceControlDTORequest();
        requestDTO.setControlAction(controlAction);

        JSONObject jsonObject = new JSONObject(gson.toJson(requestDTO));
        String url = host + POST_URL_CONTROL_DEVICE + deviceId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                null,
                null) {
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
        VolleyHelper.getSharedQueue().add(request);
    }

    @Override
    public void arm(String deviceId,long smsPhoneNumber, int armTime) throws DeviceHandlerException {
        try {
            controlDevice(deviceId, armTime);
        }catch (JSONException ex)
        {
            throw new DeviceHandlerException(GizwitzDeviceAPIHandler.class,0,ex);
        }
    }

    @Override
    public void disarm(String deviceId,long smsPhoneNumber) throws DeviceHandlerException {
        try {
            controlDevice(deviceId, DeviceControlDTORequest.CONTROL_DISARM);
        }catch (JSONException ex)
        {
            throw new DeviceHandlerException(GizwitzDeviceAPIHandler.class,0,ex);
        }
    }

    @Override
    public void homeArm(String deviceId,long smsPhoneNumber) throws DeviceHandlerException {
        try {
            controlDevice(deviceId, DeviceControlDTORequest.CONTROL_HOME_ARM);
        }catch (JSONException ex)
        {
            throw new DeviceHandlerException(GizwitzDeviceAPIHandler.class,0,ex);
        }
    }

    @Override
    public void panic(String deviceId,long phoneNumber) throws DeviceHandlerException {
        try {
            controlDevice(deviceId, DeviceControlDTORequest.CONTROL_PANIC);
        }catch (JSONException ex)
        {
            throw new DeviceHandlerException(GizwitzDeviceAPIHandler.class,0,ex);
        }
    }

    @Override
    public void login(UserDTORequest userDTORequest) {
        try {
            doLogin(userDTORequest, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    UserDTOResponse userDTOResponse = new Gson().fromJson(response.toString(), UserDTOResponse.class);
                    responseLoginListener.onLoginSuccess(userDTOResponse);
                }

                @SneakyThrows
                public void onError(VolleyError error) {
                    DeviceHandlerException exception = null;
                    if (error.networkResponse != null) {
                        String networkResponse = new String(error.networkResponse.data, "UTF-8");
                        ErrorDTOResponse errorDTOResponse = new Gson().fromJson(networkResponse, ErrorDTOResponse.class);
                        exception = new DeviceHandlerException(GizwitzDeviceAPIHandler.class,
                                errorDTOResponse.getError_code(), errorDTOResponse.getError_message(), error);

                    } else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        exception = new DeviceHandlerException(GizwitzDeviceAPIHandler.class,
                                ErrorDTOResponse.ERROR_CODE_NETWORK_ERROR, error.getMessage(), error);
                    } else if (error instanceof ServerError) {
                        exception = new DeviceHandlerException(GizwitzDeviceAPIHandler.class,
                                ErrorDTOResponse.ERROR_CODE_SERVER_ERROR, error.getMessage(), error);
                    }
                    responseLoginListener.onError(exception);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void listDevices(ListDevicesDTORequest requestDTO, Application app) throws DeviceHandlerException {
        try {
            doListDevices(requestDTO, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {

                    ListDevicesDTOResponse responseDTO = new Gson().fromJson(response.toString(), ListDevicesDTOResponse.class);
                    DeviceDao deviceDao= AppDatabase.getDatabase(app).deviceDao();
                    for(Device device:responseDTO.toDevicesModel())
                    {

                        Device byGizwitz =deviceDao.findByGizwitz(device.getGizwitzDeviceId());
                        if(byGizwitz==null)
                        {
                            if(device.getMacAddress().equals("f0fe6bc2eec0")) {
                                device.setName("Oficina");
                                device.setSmsPhoneNumber(3513570073l);
                            }
                            else if(device.getMacAddress().equals("98d86345e349"))
                                device.setName("Casa Mamá");

                            deviceDao.insert(device);
                        }
                        else{
                            byGizwitz.setIsOnline(device.getIsOnline());
                            byGizwitz.setIsLowPower(device.getIsLowPower());
                            byGizwitz.setStateLastTimestamp(device.getStateLastTimestamp());
                            deviceDao.update(device);
                        }
                    }
                    List<Device> all = deviceDao.loadApiDevices();
                    all.addAll(deviceDao.loadOnlySmsDevices());
                    all.addAll(deviceDao.loadDummyDevices());
                    responseListDevicesListener.onListDevicesSuccess(all);
                }

                @SneakyThrows
                public void onError(VolleyError error) {
                    DeviceHandlerException exception = null;
                    if (error.networkResponse != null) {
                        String networkResponse = new String(error.networkResponse.data, "UTF-8");
                        ErrorDTOResponse errorDTOResponse = new Gson().fromJson(networkResponse, ErrorDTOResponse.class);
                        exception = new DeviceHandlerException(GizwitzDeviceAPIHandler.class,
                                errorDTOResponse.getError_code(), errorDTOResponse.getError_message(), error);

                    } else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        exception = new DeviceHandlerException(GizwitzDeviceAPIHandler.class,
                                ErrorDTOResponse.ERROR_CODE_NETWORK_ERROR, error.getMessage(), error);
                    } else if (error instanceof ServerError) {
                        exception = new DeviceHandlerException(GizwitzDeviceAPIHandler.class,
                                ErrorDTOResponse.ERROR_CODE_SERVER_ERROR, error.getMessage(), error);
                    }
                    responseLoginListener.onError(exception);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void doListDevices(ListDevicesDTORequest requestDTO,final VolleyCallback callback) throws DeviceHandlerException, JSONException {
        Gson gson = new Gson();
        headers.put("x-gizwits-user-token","9e2ded83cc624f68a085e9f4e2e915bd");
        JSONObject jsonObject = new JSONObject(gson.toJson(requestDTO));
        String url = host + GET_URL_LIST_DEVICES;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, jsonObject,
                response -> callback.onSuccess(response),
                error -> callback.onError(error)) {
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
        VolleyHelper.getSharedQueue().add(request);
    }

    private void doLogin(UserDTORequest userDTORequest, final VolleyCallback callback) throws JSONException {

        Gson gson = new Gson();

        JSONObject jsonObject = new JSONObject(gson.toJson(userDTORequest));
        String url = host + POST_URL_LOGIN;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                response -> callback.onSuccess(response),
                error -> callback.onError(error)) {
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
        VolleyHelper.getSharedQueue().add(request);
    }

    @Override
    public DeviceHandler getInstance(HashMap<String, Object> handlerParams) {
        if (instance != null)
            return instance;
        instance = new GizwitzDeviceAPIHandler();
        try {

        } catch (Exception ex) {

        }
        return null;
    }

    @Override
    public void setResponseLoginListener(ResponseLoginListener listener) {
        this.responseLoginListener = listener;
    }

    @Override
    public void setResponseListDevicesListener(ResponseListDevicesListener listener) {
        this.responseListDevicesListener=listener;

    }
}
