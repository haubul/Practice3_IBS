package org.ibs.rest;

import io.restassured.filter.cookie.CookieFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

/**
 * Класс для тестирование REST API
 */

public class UiRestTest {

    /**
     * Базовый URI
     */
    String baseUri = "http://localhost:8080";

    /**
     * JSON-запрос для добавления неэкзотического товара
     */
    String requestNotExotic = "{\n" +
            "  \"name\": \"Огурец\",\n" +
            "  \"type\": \"VEGETABLE\",\n" +
            "  \"exotic\": false\n" +
            "}";

    /**
     * JSON-запрос для добавления экзотического товара
     */
    String requestExotic = "{\n" +
            "  \"name\": \"Маракуйя\",\n" +
            "  \"type\": \"FRUIT\",\n" +
            "  \"exotic\": true\n" +
            "}";

    /**
     * JSON-запрос для добавления уже существующего товара
     */
    String requestExistingProduct = "{\n" +
            "  \"name\": \"Яблоко\",\n" +
            "  \"type\": \"FRUIT\",\n" +
            "  \"exotic\": false\n" +
            "}";

    /**
     * Фильтр для работы с куки
     */
    CookieFilter cookieFilter = new CookieFilter();

    /**
     * Тест для проверки добавления и удаления новых продуктов
     */

    @Test
    void AddAndDeleteNewProductTest(){


         //POST-запрос для добавления неэкзотического товара
        Response postNotExotic = given()
                .baseUri(baseUri)
                .filter(cookieFilter)
                .contentType(ContentType.JSON)
                .body(requestNotExotic)
                .when()
                .post("/api/food");

        System.out.println("Статус код добавления: " + postNotExotic.getStatusCode());


        //POST-запрос для добавления экзотического товара
        Response postExotic = given()
                .baseUri(baseUri)
                .filter(cookieFilter)
                .contentType(ContentType.JSON)
                .body(requestExotic)
                .when()
                .post("/api/food");

        System.out.println("\nСтатус код добавления: " + postExotic.getStatusCode());


        //GET-запрос для получения списка товаров до удаления
        Response getProductBeforeDel = given()
                .baseUri(baseUri)
                .filter(cookieFilter)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/food");

        System.out.println("\nСтатус код получения: " + getProductBeforeDel.getStatusCode());
        System.out.println("Ответ:" + getProductBeforeDel.getBody().asString());
        Assertions.assertTrue(getProductBeforeDel.jsonPath().getList("name").contains("Огурец"),
                "Товар 'Огурец' не был добавлен");
        Assertions.assertTrue(getProductBeforeDel.jsonPath().getList("name").contains("Маракуйя"),
                "Товар 'Маракуйя' не был добавлен");


        //POST-запрос для удаления добавленных товаров
        Response delResponse = given()
                .baseUri(baseUri)
                .filter(cookieFilter)
                .when()
                .post("/api/data/reset");

        System.out.println("\nСтатус код удаления: " + delResponse.getStatusCode());


        //GET-запрос для получения списка товаров после удаления
        Response getProductAfterDel = given()
                .baseUri(baseUri)
                .filter(cookieFilter)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/food");
        System.out.println("\nСтатус код получения: " + getProductAfterDel.getStatusCode());
        System.out.println("Ответ:" + getProductAfterDel.getBody().asString());
        Assertions.assertFalse(getProductAfterDel.jsonPath().getList("name").contains("Огурец"),
                "Товар 'Огурец' не удален");
        Assertions.assertFalse(getProductAfterDel.jsonPath().getList("name").contains("Маракуйя"),
                "Товар 'Маракуйя' не удален");

    }


    /**
     * Тест для проверки добавления уже существующего товара
     */

    @Test
    void AddExistingProductTest(){

        //POST-запрос для добавления существующего товара
        Response postResponseExistingProduct = given()
                .baseUri(baseUri)
                .filter(cookieFilter)
                .contentType(ContentType.JSON)
                .body(requestExistingProduct)
                .when()
                .post("/api/food");

        System.out.println("Статус код добавления: " + postResponseExistingProduct.getStatusCode());

        //GET-запрос для получения списка товаров
        Response getResponseExistingProduct = given()
                .baseUri(baseUri)
                .filter(cookieFilter)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/food");

        System.out.println("\nСтатус код получения: " + getResponseExistingProduct.getStatusCode());
        System.out.println("Ответ:" + getResponseExistingProduct.getBody().asString());

        Assertions.assertFalse(getResponseExistingProduct.jsonPath().getList("name").contains("Яблоко"),
                "Товар 'Яблоко' добавился, хотя не должен был");

    }

}


