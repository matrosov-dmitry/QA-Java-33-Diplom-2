package ru.matrosov.user;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCreateUser
    {
        public String accessToken;
        Faker faker = new Faker ();
        public String name = faker.name ().firstName ();
        public String password = faker.internet ().password ();
        public String email = faker.internet ().emailAddress ();

        UserSteps step;
        UserAssert check;

        @Before
        public void setUp( ) {
            step = new UserSteps ();
            check = new UserAssert ();
        }


        @Test
        @DisplayName("Создание уникального пользователя")
        public void testCreateUser( ) {
            ValidatableResponse response = step.userCreating (name, password, email);
            check.checkStatusOk (response);
            check.checkBodySuccess (response);

            accessToken = step.getAccessToken (response);
        }


        @Test
        @DisplayName("Создание пользователя, который уже зарегистрирован")
        public void testCreateDoubleUser( ) {
            ValidatableResponse response = step.userCreating (name, password, email);

            accessToken = step.getAccessToken (response);

            ValidatableResponse responseDouble = step.userCreating (name, password, email);
            check.checkStatusForbidden (responseDouble);
            check.checkDoubleCreateBody (responseDouble);
        }

        @DisplayName("Создание пользователя, не заполнено обязательное поле")
        @Test
        public void testCreateNonCorrectUser( ) {
            ValidatableResponse response = step.userCreating (name, password, null);
            check.checkStatusForbidden (response);
            check.checkNonCorrectCreateBody (response);

            accessToken = step.getAccessToken (response);
        }

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
