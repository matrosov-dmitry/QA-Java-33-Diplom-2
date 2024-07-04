package ru.matrosov.order;


import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.matrosov.user.UserSteps;

import java.util.List;

import static ru.matrosov.constant.Constant.INVALID_INGREDIENT_HASH;

public class TestCreateOrder
    {
        public String accessToken;
        protected List<String> invalidIngredients = List.of (INVALID_INGREDIENT_HASH);
        Faker faker = new Faker ();
        public String name = faker.name ().firstName ();
        public String password = faker.internet ().password ();
        public String email = faker.internet ().emailAddress ();
        UserSteps stepsUser;
        OrderSteps stepsOrder;
        OrderAssert check;

        @Before
        public void setUp( ) {
            stepsUser = new UserSteps ();
            stepsOrder = new OrderSteps ();
            check = new OrderAssert ();
        }

        @Test
        @DisplayName("Создание заказа с авторизацией")
        public void testCreateOrderWithAuthorization( ) {
            ValidatableResponse responseUser = stepsUser.userCreating (name, password, email);
            accessToken = stepsUser.getAccessToken (responseUser);
            List<String> ingredientsMap = stepsOrder.listIngredients ();
            ValidatableResponse response = stepsOrder.orderCreatingWithAuthorization (ingredientsMap, accessToken);
            check.checkStatusOk (response);
            check.checkBodySuccess (response);
        }

        @Test
        @DisplayName("Создание заказа без авторизации")
        public void testCreateOrder( ) {
            List<String> ingredientsMap = stepsOrder.listIngredients ();
            ValidatableResponse response = stepsOrder.orderCreatingWithoutAuthorization (ingredientsMap);
            check.checkStatusUnauthorized (response);
        }

        @Test
        @DisplayName("Создание заказа без ингредиентов ")
        public void testCreateOrderWithoutIngredients( ) {
            ValidatableResponse responseUser = stepsUser.userCreating (name, password, email);
            accessToken = stepsUser.getAccessToken (responseUser);
            ValidatableResponse response = stepsOrder.orderCreatingWithAuthorization (null, accessToken);
            check.checkStatusBadRequest (response);
            check.checkBodyWithoutIngredients (response);
        }

        @Test
        @DisplayName("Создание заказа с неверным хешем ингредиентов")
        public void testCreateOrderNotValidIngredients( ) {
            ValidatableResponse responseUser = stepsUser.userCreating (name, password, email);
            accessToken = stepsUser.getAccessToken (responseUser);
            ValidatableResponse response = stepsOrder.orderCreatingWithAuthorization (invalidIngredients, accessToken);
            check.checkStatusInternal (response);


        }

        @After
        public void testDeleteUser( ) {
            try {
                stepsUser.userDeletion (accessToken);
            } catch (IllegalArgumentException e) {
                System.out.println ("Нельзя удалить пользователя если он не создан");
            }
        }
    }
