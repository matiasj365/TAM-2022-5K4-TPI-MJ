package ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceConstants;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.DeviceEvent;
import lombok.NonNull;

@Database(entities = {Device.class, DeviceEvent.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DeviceDao deviceDao();

    public abstract DeviceEventDao deviceEventDao();

    private static volatile AppDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "DeviceControlAppDatabase").allowMainThreadQueries().build();
           // loadInitialData();
        }
        return instance;
    }


    public static void loadInitialData() {
        DeviceDao dao = instance.deviceDao();
        dao.deleteAll();

        Device device = new Device();
        device.setName("Dispositivo Simulado");
        device.setServiceClass(DeviceConstants.SERVICE_MODE_DUMMY);
        dao.insert(device);

        device = new Device();
        device.setName("SMS Simulado");
        device.setServiceClass(DeviceConstants.SERVICE_MODE_SMS);
        device.setSmsPhoneNumber(3513569672l);
        dao.insert(device);
        device = dao.findBySmsPhoneNumber(3513569672l);

        List eventList = new ArrayList<>();
        DeviceEvent event = new DeviceEvent();
        event.setEventType(DeviceConstants.EVENT_ARMED);
        event.setDeviceId(device.getDeviceId());
        event.setTimestamp(System.currentTimeMillis() - 6000000);
        event.setMessage("Evento de prueba");
        eventList.add(event);

        event = new DeviceEvent();
        event.setEventType(DeviceConstants.EVENT_ARMED);
        event.setTimestamp(System.currentTimeMillis() - 6000000);
        event.setMessage("Evento de prueba");
        event.setDeviceId(device.getDeviceId());
        eventList.add(event);

        event = new DeviceEvent();
        event.setEventType(DeviceConstants.EVENT_ALARMING);
        event.setTimestamp(System.currentTimeMillis() - 5000000);
        event.setMessage("Evento de prueba");
        event.setDeviceId(device.getDeviceId());
        eventList.add(event);

        event = new DeviceEvent();
        event.setEventType(DeviceConstants.EVENT_DISARMED);
        event.setTimestamp(System.currentTimeMillis() - 4000000);
        event.setMessage("Evento de prueba");
        event.setDeviceId(device.getDeviceId());
        eventList.add(event);

        event = new DeviceEvent();
        event.setEventType(DeviceConstants.EVENT_HOME_ARMED);
        event.setTimestamp(System.currentTimeMillis() - 3000000);
        event.setMessage("Evento de prueba");
        event.setDeviceId(device.getDeviceId());
        eventList.add(event);

        event = new DeviceEvent();
        event.setEventType(DeviceConstants.EVENT_LOW_BATTERY);
        event.setTimestamp(System.currentTimeMillis() - 2000000);
        event.setMessage("Evento de prueba");
        event.setDeviceId(device.getDeviceId());
        eventList.add(event);

        event = new DeviceEvent();
        event.setEventType(DeviceConstants.EVENT_ON_LINE);
        event.setTimestamp(System.currentTimeMillis() - 1000000);
        event.setMessage("Evento de prueba");
        event.setDeviceId(device.getDeviceId());
        eventList.add(event);
        instance.deviceEventDao().deleteAll();
        instance.deviceEventDao().insertAll(eventList);
    }

    /**
     * Override the onCreate method to populate the database.
     * For this sample, we clear the database every time it is created.
     */

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
            });
        }
    };
}
