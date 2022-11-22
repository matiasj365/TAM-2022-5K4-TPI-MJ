package ar.edu.frc.utn.tam.mj.devicecontrolapp.view.navigation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.DevicesController;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.UsersController;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceControlAppException;
import lombok.SneakyThrows;

public class NavigationViewModelFactory implements  ViewModelProvider.Factory{

    @SneakyThrows
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NavigationViewModel.class)) {
            try {
                return (T) new NavigationViewModel(DevicesController.getInstance());
            } catch (DeviceControlAppException e) {
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
