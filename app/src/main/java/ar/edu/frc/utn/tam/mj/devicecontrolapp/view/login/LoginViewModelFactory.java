package ar.edu.frc.utn.tam.mj.devicecontrolapp.view.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.UsersController;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceControlAppException;
import lombok.SneakyThrows;

public class LoginViewModelFactory implements  ViewModelProvider.Factory{

    @SneakyThrows
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            try {
                return (T) new LoginViewModel(UsersController.getInstance());
            } catch (DeviceControlAppException e) {
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
