package ru.matrosov.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderAssert
    {
        @Step("Статус 200 - OK")
        protected void checkStatusOk(ValidatableResponse response) {
            response.assertThat ().statusCode (HTTP_OK);
        }

        @Step("Статус 401 - Unauthorized")
        protected void checkStatusUnauthorized(ValidatableResponse response) {
            response.assertThat ().statusCode (HTTP_UNAUTHORIZED);
        }

        @Step("Статус 400 - Bad Request")
        protected void checkStatusBadRequest(ValidatableResponse response) {
            response.assertThat ().statusCode (HTTP_BAD_REQUEST);
        }

        @Step("Статус 500 - Internal Server Error")
        protected void checkStatusInternal(ValidatableResponse response) {
            response.assertThat ().statusCode (HTTP_INTERNAL_ERROR);
        }

        @Step("Тело ответа - Ingredient ids must be provided")
        protected void checkBodyWithoutIngredients(ValidatableResponse response) {
            response.and ().body ("message", equalTo ("Ingredient ids must be provided"));
        }

        @Step("Тело ответа - NotNullValue")
        protected void checkBodyNotNullValue(ValidatableResponse response) {
            response.and ().body ("orders", notNullValue ());
        }

        @Step("Тело ответа - Success")
        protected void checkBodySuccess(ValidatableResponse response) {
            response.and ().body ("success", equalTo (true));
        }

        @Step("Тело ответа - Success")
        protected void checkBodyUnauthorized(ValidatableResponse response) {
            response.and ().body ("message", equalTo ("You should be authorised"));
        }

    }