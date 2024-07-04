package ru.matrosov.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserSteps
    {
        UserHTTPClient client = new UserHTTPClient ();

        @Step("Создание пользователя")
        public ValidatableResponse userCreating(String name, String password, String email) {
            User user = new User (name, password, email);
            return client.createUser (user);
        }

        @Step("Логин пользователя")
        public ValidatableResponse userLogging(String email, String password) {
            User user = new User (null, password, email);
            return client.loginUser (user);
        }

        @Step("Удаление пользователя")
        public ValidatableResponse userDeletion(String accessToken) {
            return client.deleteUser (accessToken);
        }

        @Step("Получение access token")
        public String getAccessToken(ValidatableResponse response) {
            return response.extract ().path ("accessToken");
        }

        @Step("Изменение данных пользователя с авторизацией")
        protected ValidatableResponse userUpdateWithAuthorization(String accessToken, String nameEdit, String passwordEdit, String emailEdit) {
            User userNew = new User (nameEdit, passwordEdit, emailEdit);
            return client.updateUserWithAuthorization (userNew, accessToken);
        }

        @Step("Изменение данных пользователя без авторизации")
        protected ValidatableResponse userUpdateWithoutAuthorization(String nameEdit, String passwordEdit, String emailEdit) {
            User userNew = new User (nameEdit, passwordEdit, emailEdit);
            return client.updateUserWithoutAuthorization (userNew);
        }
    }
