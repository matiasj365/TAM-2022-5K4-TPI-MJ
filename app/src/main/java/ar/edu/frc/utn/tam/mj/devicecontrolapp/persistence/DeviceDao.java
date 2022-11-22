package ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceConstants;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;

@Dao
public interface DeviceDao {
    @Query("SELECT * FROM device")
    List<Device> getAll();

    @Query("SELECT * FROM device WHERE deviceId IN (:deviceIds)")
    LiveData<List<Device>> loadAllByIds(int[] deviceIds);

    @Query("SELECT * FROM device WHERE serviceClass="+ DeviceConstants.SERVICE_MODE_DUMMY)
    List<Device> loadDummyDevices();

    @Query("SELECT * FROM device WHERE smsPhoneNumber is not null AND gizwitzDeviceId is null and serviceClass")
    List<Device> loadOnlySmsDevices();

    @Query("SELECT * FROM device WHERE gizwitzDeviceId is not null")
    List<Device> loadApiDevices();

    @Insert
    void insertAll(List<Device> devices);

    @Insert
    void insert(Device devices);

    @Delete
    void delete(Device device);

    @Update
    void update(Device device);

    @Query("DELETE FROM device")
    void deleteAll();

    @Query("SELECT * FROM device WHERE gizwitzDeviceId LIKE :gizwitzDeviceId LIMIT 1")
    Device findByGizwitz(String gizwitzDeviceId);

    @Query("SELECT * FROM device WHERE smsPhoneNumber = :smsPhoneNumber LIMIT 1")
    Device findBySmsPhoneNumber(long smsPhoneNumber);

}
