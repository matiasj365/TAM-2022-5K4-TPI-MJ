package ar.edu.frc.utn.tam.mj.devicecontrolapp.view.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.R;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.Result;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.UsersController;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceControlAppException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceHandlerException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.User;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginView> loginViewState = new MutableLiveData<>();
    private MutableLiveData<Result<User>> loginResult = new MutableLiveData<>();
    private UsersController usersController = null;
    private User user;

    public LoginViewModel(UsersController usersController) {
        this.usersController = usersController;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        Result.Success<User> loggedInUserViewSuccess = new Result.Success<>(user);
        loginResult.setValue(loggedInUserViewSuccess);
        this.user = user;
    }

    public void setError(DeviceControlAppException ex) {
        Result.Error error = new Result.Error(ex);
        loginResult.setValue(error);
    }

    LiveData<LoginView> getLoginView() {
        return loginViewState;
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginViewState.setValue(new LoginView(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginViewState.setValue(new LoginView(null, R.string.invalid_password));
        } else {
            loginViewState.setValue(new LoginView(true));
        }
    }

    public LiveData<Result<User>> getLoginResult() {
        return loginResult;
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return true;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try {
            usersController.login(user, this);
        } catch (DeviceHandlerException e) {
            setError(e);
        }
    }
}
