package ar.edu.frc.utn.tam.mj.devicecontrolapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class DeviceEvent {
    @PrimaryKey(autoGenerate = true)
    int idDeviceEvent;
    int deviceId;
    long timestamp;
    int eventType;
    String message;
}
