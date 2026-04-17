package com.example.project.Тронин11.utils;

import android.text.TextUtils;
import java.util.regex.Pattern;

/**
 * Утилитный класс для проверки вводимых данных.
 * Содержит методы для валидации email и пароля.
 */
public class ValidationUtils {

    // Регулярное выражение для проверки email (должен содержать @ и точку)
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Минимальная длина пароля (требование из задания)
    private static final int MIN_PASSWORD_LENGTH = 6;

    /**
     * Проверяет, является ли строка корректным email.
     * Пример правильного email: user@mail.ru
     */
    public static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Проверяет пароль на минимальную длину.
     */
    public static boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= MIN_PASSWORD_LENGTH;
    }

    /**
     * Проверяет совпадение пароля и подтверждения.
     */
    public static boolean doPasswordsMatch(String password, String confirmPassword) {
        return !TextUtils.isEmpty(password) && password.equals(confirmPassword);
    }

    /**
     * Комплексная проверка данных для входа.
     * Возвращает сообщение об ошибке или null если всё правильно.
     */
    public static String validateLoginData(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            return "Введите email";
        }
        if (!isValidEmail(email)) {
            return "Некорректный формат email";
        }
        if (TextUtils.isEmpty(password)) {
            return "Введите пароль";
        }
        return null; // Ошибок нет
    }

    /**
     * Комплексная проверка данных для регистрации.
     */
    public static String validateRegisterData(String email, String password, String confirmPassword) {
        // Проверка email
        if (TextUtils.isEmpty(email)) {
            return "Введите email";
        }
        if (!isValidEmail(email)) {
            return "Некорректный формат email";
        }

        // Проверка пароля
        if (TextUtils.isEmpty(password)) {
            return "Введите пароль";
        }
        if (!isValidPassword(password)) {
            return "Пароль должен быть не менее 6 символов";
        }

        // Проверка подтверждения
        if (TextUtils.isEmpty(confirmPassword)) {
            return "Подтвердите пароль";
        }
        if (!password.equals(confirmPassword)) {
            return "Пароли не совпадают";
        }

        return null; // Все проверки пройдены
    }
}
