package com.example.project.Тронин11.utils;

import android.text.TextUtils;
import java.util.regex.Pattern;


public class ValidationUtils {


    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");


    private static final int MIN_PASSWORD_LENGTH = 6;


    public static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }


    public static boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= MIN_PASSWORD_LENGTH;
    }


    public static boolean doPasswordsMatch(String password, String confirmPassword) {
        return !TextUtils.isEmpty(password) && password.equals(confirmPassword);
    }


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
        return null;
    }


    public static String validateRegisterData(String email, String password, String confirmPassword) {

        if (TextUtils.isEmpty(email)) {
            return "Введите email";
        }
        if (!isValidEmail(email)) {
            return "Некорректный формат email";
        }


        if (TextUtils.isEmpty(password)) {
            return "Введите пароль";
        }
        if (!isValidPassword(password)) {
            return "Пароль должен быть не менее 6 символов";
        }


        if (TextUtils.isEmpty(confirmPassword)) {
            return "Подтвердите пароль";
        }
        if (!password.equals(confirmPassword)) {
            return "Пароли не совпадают";
        }

        return null;
    }
}
