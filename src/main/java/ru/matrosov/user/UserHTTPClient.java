package ru.matrosov.user;

import io.restassured.response.ValidatableResponse;
import ru.matrosov.client.HTTPClient;

import static ru.matrosov.constant.Constant.CREATE_USER;
import static ru.matrosov.constant.Constant.LOGIN_USER;

public class UserHTTPClient extends HTTPClient
    {
        public ValidatableResponse createUser(User user) {
            return postRequestWithoutAuthorization (CREATE_USER, user)
                    .then ().log ().all ();
        }

        public ValidatableResponse loginUser(User user) {
            return postRequestWithoutAuthorization (LOGIN_USER, user)
                    .then ().log ().all ();
        }

        public ValidatableResponse deleteUser(String accessToken) {
            return deleteRequest (accessToken)
                    .then ().log ().all ();
        }

        public ValidatableResponse updateUserWithAuthorization(User user, String accessToken) {
            return patchRequestWithAuthorization (user, accessToken)
                    .then ().log ().all ();
        }

        public ValidatableResponse updateUserWithoutAuthorization(User user) {
            return patchRequestWithoutAuthorization (user)
                    .then ().log ().all ();
        }
    }
