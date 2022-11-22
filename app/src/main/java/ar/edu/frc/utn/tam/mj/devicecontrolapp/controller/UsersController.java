package ar.edu.frc.utn.tam.mj.devicecontrolapp.controller;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceConstants;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceHandler;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceHandlerFactory;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.ResponseLoginListener;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.UserDTORequest;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.UserDTOResponse;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceControlAppException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceHandlerException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.User;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.login.LoginViewModel;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class UsersController implements ResponseLoginListener {

    private static UsersController instance;

    private UsersController() {
    }

    private static DeviceHandler deviceHandler;


    public static UsersController getInstance() throws DeviceControlAppException {
        if (instance == null) {
            instance = new UsersController();
            deviceHandler = DeviceHandlerFactory.getInstance(DeviceConstants.CLASS_SERVICE_API_HANDLER);
        }
        return instance;
    }

    LoginViewModel loginViewModel;

    User user;
    public void login(User user, LoginViewModel loginViewModel) throws DeviceHandlerException {
        this.loginViewModel = loginViewModel;
        this.user=user;
        UserDTORequest userDTORequest=new UserDTORequest();
        userDTORequest.username= user.getUsername();
        userDTORequest.password= user.getPassword();
        deviceHandler.setResponseLoginListener(this);
        deviceHandler.login(userDTORequest);
    }

    public void logout() {
        // TODO: revoke authentication
    }

    @Override
    public void onLoginSuccess(UserDTOResponse userDTOResponse) {
        user.setToken(userDTOResponse.getToken());
        user.setTokenExpireAt(userDTOResponse.getExpire_at());
        loginViewModel.setUser(user);
    }

    @Override
    public void onError(DeviceHandlerException exception) {
        loginViewModel.setError(exception);
    }
}