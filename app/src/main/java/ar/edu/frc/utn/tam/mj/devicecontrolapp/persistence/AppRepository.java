package ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;

public class AppRepository {
    private DeviceDao deviceDao;
    private DeviceEventDao deviceEventDao;
    private static AppRepository instance;

    private AppRepository(Application application){
        AppDatabase db=AppDatabase.getDatabase(application);
        deviceDao=db.deviceDao();
        deviceEventDao=db.deviceEventDao();
    }

    public static AppRepository getInstance(Application application)
    {
        if(instance==null)
            instance=new AppRepository(application);
        return instance;
    }


    public LiveData<List<Device>> getSmsDevices() {
        return new MutableLiveData<>(deviceDao.loadOnlySmsDevices());
    }

    public LiveData<List<Device>> getDummyDevicesSms() {
        return new MutableLiveData<>(deviceDao.loadDummyDevices());
    }

    public LiveData<List<Device>> getApiDevices() {
        return new MutableLiveData<>(deviceDao.loadApiDevices());
    }
}
