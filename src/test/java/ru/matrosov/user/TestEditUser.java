package ru.matrosov.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestEditUser
    {
        public String accessToken;

        Faker faker = new Faker ();

        public String name = faker.name ().firstName ();
        public String password = faker.internet ().password ();
        public String email = faker.internet ().emailAddress ();

        public String nameEdit = faker.name ().firstName ();
        public String passwordEdit = faker.internet ().password ();
        public String emailEdit = faker.internet ().emailAddress ();

        UserSteps step;
        UserAssert check;

        @Before
        public void createUser( ) {
            step = new UserSteps ();
            check = new UserAssert ();

            ValidatableResponse response = step.userCreating (name, password, email);
            accessToken = step.getAccessToken (response);
        }

        @Test
        @DisplayName("Изменение данных пользователя с авторизацией")
        public void testEditUser( ) {
            ValidatableResponse response =
                    step.userUpdateWithAuthorization (accessToken, nameEdit, passwordEdit, emailEdit);
            check.checkStatusOk (response);
            check.checkBodySuccess (response);
        }

        @Test
        @DisplayName("Изменение данных пользователя без авторизации")
        public void testEditUserWithoutAuthorization( ) {
            ValidatableResponse response =
                    step.userUpdateWithoutAuthorization (nameEdit, passwordEdit, emailEdit);
            check.checkStatusUnauthorized (response);
            check.checkNonCorrectEditBody (response);
        }

        @Step("Удаление пользователя")
        @After
        public void testDeleteUser( ) {
            try {
                ValidatableResponse response = step.userDeletion (accessToken);
                check.checkStatusAccepted (response);
                check.checkDeleteBody (response);
            } catch (IllegalArgumentException e) {
                System.out.println ("Нельзя удалить пользователя если он не создан");
            }
        }

    }
