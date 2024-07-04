package ru.matrosov.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;

public class UserAssert
    {
        @Step("Статус 200 - OK")
        protected void checkStatusOk(ValidatableResponse response) {
            response.assertThat ().statusCode (HTTP_OK);
        }

        @Step("Статус 202 - Accepted")
        protected void checkStatusAccepted(ValidatableResponse response) {
            response.assertThat ().statusCode (HTTP_ACCEPTED);
        }

        @Step("Статус 403 - Forbidden")
        protected void checkStatusForbidden(ValidatableResponse response) {
            response.assertThat ().statusCode (HTTP_FORBIDDEN);
        }

        @Step("Статус 401 - Unauthorized")
        protected void checkStatusUnauthorized(ValidatableResponse response) {
            response.assertThat ().statusCode (HTTP_UNAUTHORIZED);
        }

        @Step("Тело ответа - Success")
        protected void checkBodySuccess(ValidatableResponse response) {
            response.and ().body ("success", equalTo (true));
        }

        @Step("Тело ответа - User already exists")
        protected void checkDoubleCreateBody(ValidatableResponse response) {
            response.and ().body ("message", equalTo ("User already exists"));
        }

        @Step("Тело ответа - Email, password and name are required fields")
        protected void checkNonCorrectCreateBody(ValidatableResponse response) {
            response.and ().body ("message", equalTo ("Email, password and name are required fields"));
        }

        @Step("Тело ответа - User successfully removed")
        protected void checkDeleteBody(ValidatableResponse response) {
            response.and ().body ("message", equalTo ("User successfully removed"));
        }

        @Step("Тело ответа - email or password are incorrect")
        protected void checkNonCorrectLoginBody(ValidatableResponse response) {
            response.and ().body ("message", equalTo ("email or password are incorrect"));
        }

        @Step("Тело ответа - You should be authorised")
        protected void checkNonCorrectEditBody(ValidatableResponse response) {
            response.and ().body ("message", equalTo ("You should be authorised"));
        }


    }

