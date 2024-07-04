package ru.matrosov.order;

import io.restassured.response.ValidatableResponse;
import ru.matrosov.client.HTTPClient;

import static ru.matrosov.constant.Constant.*;

public class OrderHTTPClient extends HTTPClient
    {
        public ValidatableResponse createOrderWithAuthorization(Order order, String accessToken) {
            return postRequestWithAuthorization (CREATE_ORDER, order, accessToken)
                    .then ().log ().all ();
        }

        public ValidatableResponse createOrderWithoutAuthorization(Order order) {
            return postRequestWithoutAuthorization (CREATE_ORDER, order)
                    .then ().log ().all ();
        }

        public ValidatableResponse getIngredients( ) {
            return getRequestWithoutAuthorization (GET_INGREDIENTS)
                    .then ().log ().all ();
        }

        public ValidatableResponse getInfoOrderWithAuthorization(String accessToken) {
            return getRequestWithAuthorization (INFO_ORDER, accessToken)
                    .then ().log ().all ();
        }

        public ValidatableResponse getInfoOrderWithoutAuthorization( ) {
            return getRequestWithoutAuthorization (INFO_ORDER)
                    .then ().log ().all ();
        }
    }
