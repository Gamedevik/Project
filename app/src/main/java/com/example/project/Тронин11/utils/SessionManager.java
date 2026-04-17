package com.example.project.Тронин11.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Менеджер сессии пользователя.
 * Сохраняет информацию о том, что пользователь вошел в систему.
 * Использует SharedPreferences - хранилище настроек Android.
 */
public class SessionManager {

    // Имя файла с настройками
    private static final String PREF_NAME = "UserSessionPrefs";

    // Ключи для сохранения данных
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_ID = "userId";

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    private static SessionManager instance;

    // Приватный конструктор
    private SessionManager(Context context) {
        preferences = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    // Получить экземпляр класса
    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    /**
     * Сохранить сессию после успешного входа.
     * @param userId ID пользователя из базы
     * @param email Email пользователя
     */
    public void createLoginSession(int userId, String email) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USER_EMAIL, email);
        editor.apply(); // apply() - асинхронное сохранение
    }

    /**
     * Проверить, вошел ли пользователь.
     */
    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /**
     * Получить email текущего пользователя.
     */
    public String getUserEmail() {
        return preferences.getString(KEY_USER_EMAIL, "");
    }

    /**
     * Получить ID текущего пользователя.
     */
    public int getUserId() {
        return preferences.getInt(KEY_USER_ID, -1);
    }

    /**
     * Выход из системы - очистка всех данных.
     */
    public void logout() {
        editor.clear();
        editor.apply();
    }
}