package ru.netology.test;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class Data {
    private Data(){}

    private static Faker faker = new Faker(new Locale("en"));

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void Registration(User registration) {
        given()
                .spec(requestSpec)
                .body(registration)

                .when()
                .post("/api/system/users")

                .then()
                .statusCode(200);
    }

    public static User generateNewActiveUser() {
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        Registration(new User(login, password, "active"));
        return new User(login, password, "active");
    }

    public static User generateNewBlockedUser() {
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        Registration(new User(login, password, "blocked"));
        return new User(login, password, "blocked");
    }

    public static User generateNewUserWithInvalidLogin() {
        String password = faker.internet().password();
        String status = "active";
        Registration(new User("vasya", password, status));
        return new User("ivan", password, status);
    }

    public static User generateNewUserWithInvalidPassword() {
        String login = faker.name().firstName().toLowerCase();
        String status = "active";
        Registration(new User(login, "password", status));
        return new User(login, "qwerty", status);
    }
}
