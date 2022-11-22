package ar.edu.frc.utn.tam.mj.devicecontrolapp.view.login;

import android.app.Activity;

import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.security.keystore.KeyGenParameterSpec;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.DeviceControllApplication;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.Result;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceControlAppException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.User;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.DesktopActivity;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.databinding.ActivityLoginBinding;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.UIConstants;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.UIUtils;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        loginViewModel.getLoginView().observe(this, new Observer<LoginView>() {
            @Override
            public void onChanged(@Nullable LoginView loginView) {
                if (loginView == null) {
                    return;
                }
                loginButton.setEnabled(loginView.isDataValid());
                if (loginView.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginView.getUsernameError()));
                }
                if (loginView.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginView.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<Result<User>>() {
            @Override
            public void onChanged(@Nullable Result<User> loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult instanceof Result.Error) {

                    int errorCode = UIUtils.getErrorMessage(((Result.Error) loginResult).getError());
                    showLoginFailed(errorCode);
                }
                if (loginResult instanceof Result.Success) {

                    updateUiWithUser(((Result.Success<User>) loginResult).getData());
                    setResult(Activity.RESULT_OK);

                    //Complete and destroy login activity once successful
                    finish();

                }
            }
        });

        SharedPreferences sharedPreferences = UIUtils.getSharedPreferences(this);
        if (sharedPreferences != null && sharedPreferences.contains(UIConstants.SP_EMAIL) && sharedPreferences.contains(UIConstants.SP_PASSWORD)) {
            usernameEditText.setText(sharedPreferences.getString(UIConstants.SP_EMAIL, ""));
            passwordEditText.setText(sharedPreferences.getString(UIConstants.SP_PASSWORD, ""));
            binding.swRememberme.setChecked(true);
            loginButton.setEnabled(true);
        } else {
            binding.swRememberme.setChecked(false);
        }

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) ->

        {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        loginButton.setOnClickListener(v ->

        {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });
    }

    private void updateUiWithUser(User model) {
        //String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        //Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        final Switch swRememberme = binding.swRememberme;
        SharedPreferences.Editor editor = UIUtils.getSharedPreferences(this).edit();
        editor.putString(UIConstants.SP_TOKEN, model.getToken());
        editor.putLong(UIConstants.SP_TOKEN_EXPIRE_AT, model.getTokenExpireAt());
        if (swRememberme.isChecked()) {
            editor.putString(UIConstants.SP_EMAIL, model.getUsername());
            editor.putString(UIConstants.SP_PASSWORD, model.getPassword());
        } else {
            editor.remove(UIConstants.SP_EMAIL);
            editor.remove(UIConstants.SP_PASSWORD);

        }
        editor.commit();
        Intent intent = new Intent(this, DesktopActivity.class);
        startActivity(intent);
    }


    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_LONG).show();
    }
}