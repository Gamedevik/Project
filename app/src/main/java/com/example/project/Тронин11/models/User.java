package com.example.project.Тронин11.models;

/**
 * Модель данных пользователя.
 * Содержит информацию о пользователе: id, email, пароль.
 */
public class User {

    private int id;           // Уникальный номер в базе данных
    private String email;     // Email пользователя (логин)
    private String password;  // Пароль пользователя

    // Конструктор для создания нового пользователя (без ID)
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Конструктор для пользователя из базы данных (с ID)
    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}