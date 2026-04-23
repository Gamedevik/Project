package com.example.project.Тронин11.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.Тронин11.data.DatabaseHelper;
import com.example.project.Тронин11.models.User;
import com.example.project.Тронин11.utils.SessionManager;
import com.example.project.Тронин11.utils.ValidationUtils;


public class LoginActivity extends AppCompatActivity {


    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvGoToRegister;


    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        databaseHelper = DatabaseHelper.getInstance(this);
        sessionManager = SessionManager.getInstance(this);


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);


        btnLogin.setOnClickListener(v -> attemptLogin());
        tvGoToRegister.setOnClickListener(v -> {

            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }


    private void attemptLogin() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();


        etEmail.setError(null);
        etPassword.setError(null);


        String error = ValidationUtils.validateLoginData(email, password);
        if (error != null) {

            if (email.isEmpty() || !ValidationUtils.isValidEmail(email)) {
                etEmail.setError(error);
            } else {
                etPassword.setError(error);
            }
            return;
        }


        btnLogin.setEnabled(false);
        btnLogin.setText("Вход...");


        new Thread(() -> {
            User user = databaseHelper.loginUser(email, password);


            runOnUiThread(() -> {
                btnLogin.setEnabled(true);
                btnLogin.setText("Войти");

                if (user != null) {

                    sessionManager.createLoginSession(user.getId(), user.getEmail());
                    Toast.makeText(this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(this, "Неверный email или пароль", Toast.LENGTH_LONG).show();
                    etPassword.setError("Неверный пароль");
                }
            });
        }).start();
    }
}