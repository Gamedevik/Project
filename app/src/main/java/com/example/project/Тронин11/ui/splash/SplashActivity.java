package com.example.project.Тронин11.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.Тронин11.ui.auth.LoginActivity;
import com.example.project.Тронин11.utils.SessionManager;

/**
 * Экран-заставка при запуске приложения.
 * Проверяет, вошел ли пользователь, и направляет на нужный экран.
 */
public class SplashActivity extends AppCompatActivity {

    // Задержка в миллисекундах (1.5 секунды)
    private static final long SPLASH_DELAY_MS = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Ждем указанное время, затем проверяем авторизацию
        new Handler(Looper.getMainLooper())
                .postDelayed(this::checkAuthentication, SPLASH_DELAY_MS);
    }

    /**
     * Проверяет, авторизован ли пользователь.
     * Если да - на главный экран, если нет - на экран входа.
     */
    private void checkAuthentication() {
        SessionManager sessionManager = SessionManager.getInstance(this);

        if (sessionManager.isLoggedIn()) {
            // Пользователь уже входил - идем на главный экран
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            // Пользователь не входил - идем на экран входа
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        finish(); // Закрываем SplashActivity
    }
}