package ar.edu.frc.utn.tam.mj.devicecontrolapp.view.login;

import androidx.annotation.Nullable;

import lombok.Data;

@Data
public class LoginView {

    LoginView(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }
    LoginView (boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    String email;
    String password;
    boolean rememberMe;

    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;
}
