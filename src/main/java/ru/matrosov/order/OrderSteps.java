package ru.matrosov.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Collections;
import java.util.List;

public class OrderSteps
    {
        OrderHTTPClient client = new OrderHTTPClient ();

        @Step("Создание заказа с авторизацией")
        protected ValidatableResponse orderCreatingWithAuthorization(List<String> ingredientsMap, String accessToken) {
            Order order = new Order ((List<String>) ingredientsMap);
            return client.createOrderWithAuthorization (order, accessToken);
        }

        @Step("Получение данных об ингредиентах")
        protected List<String> listIngredients( ) {
            List<String> ingredients = client.getIngredients ().extract ().path ("data._id");
            Collections.shuffle (ingredients);
            List<String> ingredientsMap = List.of (ingredients.get (0), ingredients.get (1));
            return ingredientsMap;
        }

        @Step("Создание заказа без авторизации")
        protected ValidatableResponse orderCreatingWithoutAuthorization(List<String> ingredientsMap) {
            Order order = new Order ((List<String>) ingredientsMap);
            return client.createOrderWithoutAuthorization (order);
        }

        @Step("Получение заказов конкретного пользователя без авторизации")
        protected ValidatableResponse gettingInfoOrder(String accessToken) {
            return client.getInfoOrderWithAuthorization (accessToken);
        }

        @Step("Получение заказов конкретного авторизированного пользователя")
        protected ValidatableResponse gettingInfoOrderWithoutAuthorization( ) {
            return client.getInfoOrderWithoutAuthorization ();
        }

    }
