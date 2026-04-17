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

/**
 * Экран регистрации нового пользователя.
 * Пользователь вводит email, пароль и подтверждение пароля.
 */
public class RegisterActivity extends AppCompatActivity {

    // UI элементы
    private EditText etEmail;              // Поле email
    private EditText etPassword;           // Поле пароля
    private EditText etConfirmPassword;    // Поле подтверждения
    private Button btnRegister;            // Кнопка регистрации
    private TextView tvGoToLogin;          // Ссылка на вход

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = DatabaseHelper.getInstance(this);

        // Привязка элементов
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvGoToLogin = findViewById(R.id.tvGoToLogin);

        // Обработчики
        btnRegister.setOnClickListener(v -> attemptRegister());
        tvGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    /**
     * Попытка регистрации нового пользователя.
     */
    private void attemptRegister() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Очищаем ошибки
        etEmail.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);

        // Валидация
        String error = ValidationUtils.validateRegisterData(email, password, confirmPassword);
        if (error != null) {
            // Показываем ошибку в нужном поле
            if (!ValidationUtils.isValidEmail(email)) {
                etEmail.setError(error);
            } else if (!ValidationUtils.isValidPassword(password)) {
                etPassword.setError(error);
            } else {
                etConfirmPassword.setError(error);
            }
            return;
        }

        // Блокируем кнопку
        btnRegister.setEnabled(false);
        btnRegister.setText("Регистрация...");

        // Регистрация в фоновом потоке
        new Thread(() -> {
            boolean success = databaseHelper.registerUser(email, password);

            runOnUiThread(() -> {
                btnRegister.setEnabled(true);
                btnRegister.setText("Зарегистрироваться");

                if (success) {
                    // Успешная регистрация
                    Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_LONG).show();

                    // Возвращаемся на экран входа
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("prefilled_email", email);
                    startActivity(intent);
                    finish();
                } else {
                    // Email уже занят
                    etEmail.setError("Этот email уже зарегистрирован");
                }
            });
        }).start();
    }
}