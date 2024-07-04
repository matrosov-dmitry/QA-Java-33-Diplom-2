package ru.matrosov.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static ru.matrosov.constant.Constant.*;


public class HTTPClient
    {
        @Step("RequestSpec")
        protected RequestSpecification requestSpecification( ) {
            return given ()
                    .log ().all ()
                    .contentType ("application/json")
                    .baseUri (BASE_URL);
        }

        @Step("POST request without Authorization")
        protected Response postRequestWithoutAuthorization(String path, Object body) {
            return given ()
                    .spec (requestSpecification ())
                    .body (body)
                    .post (path);

        }

        @Step("POST request with Authorization")
        protected Response postRequestWithAuthorization(String path, Object body, String accessToken) {
            return given ()
                    .spec (requestSpecification ())
                    .header ("Authorization", accessToken)
                    .body (body)
                    .post (path);
        }

        @Step("DELETE request")
        protected Response deleteRequest(String accessToken) {
            return given ()
                    .spec (requestSpecification ())
                    .header ("Authorization", accessToken)
                    .delete (DELETE_USER);
        }

        @Step("PATCH request with Authorization")
        protected Response patchRequestWithAuthorization(Object body, String accessToken) {
            return given ()
                    .spec (requestSpecification ())
                    .header ("Authorization", accessToken)
                    .body (body)
                    .patch (EDIT_USER);
        }

        @Step("PATCH request without Authorization")
        protected Response patchRequestWithoutAuthorization(Object body) {
            return given ()
                    .spec (requestSpecification ())
                    .body (body)
                    .patch (EDIT_USER);
        }

        @Step("GET request without Authorization")
        protected Response getRequestWithoutAuthorization(String path) {
            return given ()
                    .spec (requestSpecification ())
                    .get (path);
        }

        @Step("GET request with Authorization")
        protected Response getRequestWithAuthorization(String path, String accessToken) {
            return given ()
                    .spec (requestSpecification ())
                    .header ("Authorization", accessToken)
                    .get (path);
        }

    }
