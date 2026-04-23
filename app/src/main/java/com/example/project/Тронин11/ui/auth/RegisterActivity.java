package com.example.project.Тронин11.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project.R;
import com.example.project.Тронин11.data.DatabaseHelper;
import com.example.project.Тронин11.utils.ValidationUtils;


public class RegisterActivity extends AppCompatActivity {


    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private TextView tvGoToLogin;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = DatabaseHelper.getInstance(this);


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvGoToLogin = findViewById(R.id.tvGoToLogin);

        btnRegister.setOnClickListener(v -> attemptRegister());
        tvGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }


    private void attemptRegister() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();


        etEmail.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);


        String error = ValidationUtils.validateRegisterData(email, password, confirmPassword);
        if (error != null) {

            if (!ValidationUtils.isValidEmail(email)) {
                etEmail.setError(error);
            } else if (!ValidationUtils.isValidPassword(password)) {
                etPassword.setError(error);
            } else {
                etConfirmPassword.setError(error);
            }
            return;
        }


        btnRegister.setEnabled(false);
        btnRegister.setText("Регистрация...");


        new Thread(() -> {
            boolean success = databaseHelper.registerUser(email, password);

            runOnUiThread(() -> {
                btnRegister.setEnabled(true);
                btnRegister.setText("Зарегистрироваться");

                if (success) {
                    Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("prefilled_email", email);
                    startActivity(intent);
                    finish();
                } else {
                    etEmail.setError("Этот email уже зарегистрирован");
                }
            });
        }).start();
    }
}