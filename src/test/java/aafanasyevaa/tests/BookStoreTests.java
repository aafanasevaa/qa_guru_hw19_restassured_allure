package aafanasyevaa.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static aafanasyevaa.filters.CustomLogFilter.customLogFilter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import com.codeborne.selenide.Configuration;


public class BookStoreTests {

    @BeforeAll
    static void configureUrl() {
        RestAssured.baseURI = "https://demoqa.com";
        Configuration.baseUrl = "https://demoqa.com";
    }

    @Test
    @Tag("API")
    void deleteBookWithoutAuthorizationTest() {

        String data = "{\"UserId\": \"1\"}";

        step("Delete book", () ->
                given()
                        .filter(customLogFilter().withCustomTemplates())
                        .contentType("application/json")
                        .accept("application/json")
                        .body(data)
                        .when()
                        .delete("/BookStore/v1/Books")
                        .then()
                        .log().body()
                        .body("code", is("1200"))
                        .body("message", is("User not authorized!"))
        );
    }

    @Test
    @Tag("API")
    void findBookWithIncorrectIdTest() {

        String data = "{\"UserId\": \"1\"}";

        step("Find book", () ->
                given()
                        .filter(customLogFilter().withCustomTemplates())
                        .contentType("application/json")
                        .accept("application/json")
                        .body(data)
                        .when()
                        .get("/Account/v1/User/1")
                        .then()
                        .log().body()
                        .body("code", is("1200"))
                        .body("message", is("User not authorized!"))
        );
    }
}
