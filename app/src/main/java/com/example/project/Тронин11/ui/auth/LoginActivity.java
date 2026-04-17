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

/**
 * Экран входа в систему.
 * Пользователь вводит email и пароль, система проверяет их в базе данных.
 */
public class LoginActivity extends AppCompatActivity {

    // UI элементы
    private EditText etEmail;           // Поле для ввода email
    private EditText etPassword;        // Поле для ввода пароля
    private Button btnLogin;            // Кнопка "Войти"
    private TextView tvGoToRegister;    // Ссылка на регистрацию

    // Зависимости
    private DatabaseHelper databaseHelper;  // Работа с базой данных
    private SessionManager sessionManager;   // Сохранение сессии

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Инициализация
        databaseHelper = DatabaseHelper.getInstance(this);
        sessionManager = SessionManager.getInstance(this);

        // Привязка элементов интерфейса
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);

        // Обработчики нажатий
        btnLogin.setOnClickListener(v -> attemptLogin());
        tvGoToRegister.setOnClickListener(v -> {
            // Переход на экран регистрации
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }

    /**
     * Попытка входа в систему.
     * Проверяет введенные данные и сверяет с базой данных.
     */
    private void attemptLogin() {
        // Получаем текст из полей
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Очищаем старые ошибки
        etEmail.setError(null);
        etPassword.setError(null);

        // Проверяем данные через ValidationUtils
        String error = ValidationUtils.validateLoginData(email, password);
        if (error != null) {
            // Показываем ошибку в нужном поле
            if (email.isEmpty() || !ValidationUtils.isValidEmail(email)) {
                etEmail.setError(error);
            } else {
                etPassword.setError(error);
            }
            return;
        }

        // Блокируем кнопку на время проверки
        btnLogin.setEnabled(false);
        btnLogin.setText("Вход...");

        // Проверяем в базе данных (в отдельном потоке)
        new Thread(() -> {
            User user = databaseHelper.loginUser(email, password);

            // Возвращаемся в главный поток для обновления UI
            runOnUiThread(() -> {
                btnLogin.setEnabled(true);
                btnLogin.setText("Войти");

                if (user != null) {
                    // Успешный вход
                    sessionManager.createLoginSession(user.getId(), user.getEmail());
                    Toast.makeText(this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();

                    // Переход на главный экран
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    // Неверный логин или пароль
                    Toast.makeText(this, "Неверный email или пароль", Toast.LENGTH_LONG).show();
                    etPassword.setError("Неверный пароль");
                }
            });
        }).start();
    }
}