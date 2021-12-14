package com.geekbrains.backend.test.imgur;

import com.geekbrains.backend.test.FunctionalTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


public class ImgurApiFunctionalTest extends FunctionalTest {

    private static Properties properties;
    private static String TOKEN;

    // зачитывание проперти файла
    @BeforeAll
    static void beforeAll() throws Exception {
        properties = readProperties();
        RestAssured.baseURI = properties.getProperty("imgur-api-URL");
        TOKEN = properties.getProperty("imgur-api-token");
    }

    @Test
    void getAccountBase() {
        String userName = "irynapak";
        given()
                .auth()
                .oauth2(TOKEN)
                .log()
                .all()
                .expect()
                .body("data.id", is(157814665))
                .log()
                .all()
                .when()
                .get("account/" + userName);
    }

    @Test
    void postImageTest() {
        given()
                .auth()
                .oauth2(TOKEN)
                .multiPart("image", getFileResource("lesson3.jpg"))
                .formParam("name", "CAFE FOREVER")
                .formParam("title", "Raccoon and coffee")
                .log()
                .all()
                .expect()
                .body("data.name", is("CAFE FOREVER"))
                .body("data.title", is("Raccoon and coffee"))
                .body("data.size", is(1174437))
                .body("data.type", is("image/jpeg"))
                .log()
                .all()
                .when()
                .post("upload");
    }

    @Test
    void postImageBadRequestTest() {
        given()
                .auth()
                .oauth2(TOKEN)
                .multiPart("image", getFileResource("Lesson3_text.txt"))
                .log()
                .all()
                .expect()
                .contentType("application/json; charset=utf-8")
                .body("data.error", is("We don't support that file type!"))
                .body("status",is(400))
                .log()
                .all()
                .when()
                .post("upload");
    }

    @Test
    void getImageHash(){
        String imageHash = "hGp8ZSg";
        given()
                .auth()
                .oauth2(TOKEN)
                .log()
                .all()
                .expect()
                .log()
                .all()
                .when()
                .get("account/" + imageHash);
    }

    @Test
    void getAccountImage() {
        String userName = "irynapak";
        given()
                .auth()
                .oauth2(TOKEN)
                .log()
                .all()
                .expect()
                .contentType("application/json")
                .body("status",is(200))
                .log()
                .all()
                .when()
                .get("account/" + "me/" + "images");
    }

    @Test
    void postUpdateImageInfo(){
        String imageHashUpdate = "qEDkjAN";
        given()
                .auth()
                .oauth2(TOKEN)
                .formParam("title", "very interesting")
                .log()
                .all()
                .expect()
                .contentType("application/json")
                .body("status",is(200))
                .log()
                .all()
                .when()
                .post("image/" + imageHashUpdate);
    }

    @Test
    void delImageHash(){
        String delImageHash = "YOeAlRZy7D0UXMd";
        given()
                .auth()
                .oauth2(TOKEN)
                .log()
                .all()
                .expect()
                .contentType("application/json")
                .body("status",is(200))
                .log()
                .all()
                .when()
                .delete("image/" + delImageHash);
    }
}
