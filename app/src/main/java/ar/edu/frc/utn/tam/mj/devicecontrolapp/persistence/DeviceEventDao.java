package ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.DeviceEvent;

@Dao
public interface DeviceEventDao {
    @Query("SELECT * FROM deviceEvent")
    List<DeviceEvent> getAll();

    @Insert
    void insertAll(List<DeviceEvent> deviceEvents);

    @Insert
    void insert(DeviceEvent devices);

    @Delete
    void delete(DeviceEvent deviceEvent);

    @Query("DELETE FROM deviceEvent")
    void deleteAll();

    @Update
    void update(DeviceEvent deviceEvent);

    @Query("SELECT * FROM deviceEvent WHERE deviceId =:deviceId")
    List<DeviceEvent> loadAllByDeviceEventId(int deviceId);

}
