package ru.matrosov.order;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.matrosov.user.UserSteps;

import java.util.List;

public class TestGetInfoOrder
    {
        public String accessToken;

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
        @DisplayName("Получение заказов конкретного пользователя с авторизацией")
        public void testGetInfoOrderWithAuthorization( ) {
            ValidatableResponse responseUser = stepsUser.userCreating (name, password, email);
            accessToken = stepsUser.getAccessToken (responseUser);
            List<String> ingredientsMap = stepsOrder.listIngredients ();
            ValidatableResponse response = stepsOrder.orderCreatingWithAuthorization (ingredientsMap, accessToken);
            ValidatableResponse responseInfo = stepsOrder.gettingInfoOrder (accessToken);
            check.checkStatusOk (responseInfo);
            check.checkBodyNotNullValue (responseInfo);

        }

        @Test
        @DisplayName("Получение заказов конкретного пользователя без авторизации")
        public void testGetInfoOrderWithoutAuthorization( ) {
            ValidatableResponse responseUser = stepsUser.userCreating (name, password, email);
            accessToken = stepsUser.getAccessToken (responseUser);
            List<String> ingredientsMap = stepsOrder.listIngredients ();
            stepsOrder.orderCreatingWithAuthorization (ingredientsMap, accessToken);
            ValidatableResponse responseInfo = stepsOrder.gettingInfoOrderWithoutAuthorization ();
            check.checkStatusUnauthorized (responseInfo);
            check.checkBodyUnauthorized (responseInfo);
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
