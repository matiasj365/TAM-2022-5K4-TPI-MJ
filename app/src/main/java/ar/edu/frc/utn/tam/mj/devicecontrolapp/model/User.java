package ar.edu.frc.utn.tam.mj.devicecontrolapp.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class User {
    Integer userId;
    String username;
    String token;
    Long tokenExpireAt;
    String password;
    ArrayList<Device> devices;
}
