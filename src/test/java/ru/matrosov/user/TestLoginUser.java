package ru.matrosov.user;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLoginUser
    {
        public String accessToken;

        Faker faker = new Faker ();

        public String name = faker.name ().firstName ();
        public String password = faker.internet ().password ();
        public String email = faker.internet ().emailAddress ();

        public String fakedPassword = faker.internet ().password ();
        public String fakedEmail = faker.internet ().emailAddress ();

        UserSteps step;
        UserAssert check;

        @Before
        public void createUser( ) {
            step = new UserSteps ();
            check = new UserAssert ();

            ValidatableResponse response =
                    step.userCreating (name, password, email);
            accessToken = step.getAccessToken (response);
        }

        @DisplayName("Логин под существующим пользователем")
        @Test
        public void testLoginUser( ) {
            ValidatableResponse response = step.userLogging (email, password);
            check.checkStatusOk (response);
            check.checkBodySuccess (response);
        }

        @DisplayName("Логин с неверным логином и паролем")
        @Test
        public void testFakedUser( ) {
            ValidatableResponse response = step.userLogging (fakedEmail, fakedPassword);
            check.checkStatusUnauthorized (response);
            check.checkNonCorrectLoginBody (response);
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
