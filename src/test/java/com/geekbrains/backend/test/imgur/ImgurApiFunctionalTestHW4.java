package com.geekbrains.backend.test.imgur;

import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiFunctionalTestHW4 extends ImgurApiAbstractTestHW4 {

       private static String CURRENT_IMAGE_ID;

        /* Запрос на мой аккаунт*/
        @DisplayName("Тест запроса информации об аккаунте")
        @Test
        @Order(1)
        void getAccountBase() {
            String userName = "irynapak";
            given()
                    .spec(requestSpecification)
                    .log()
                    .all()
                    .expect()
                    .body("data.id", is(157814665))
                    .log()
                    .all()
                    .when()
                    .get("account/" + userName);
        }

        /* Загрузка моей картинки с моими параметрами*/
        @DisplayName("Тест загрузки картинки")
        @Test
        @Order(2)
        void postImageTest() {
            CURRENT_IMAGE_ID = given()
                    .spec(requestSpecification)
                    .multiPart("image", getFileResource("lesson4.jpg"))
                    .formParam("name", "Nostalgia")
                    .formParam("title", "My city")
                    .log()
                    .all()
                    .expect()
                    .header("Content-Type", "application/json; charset=utf-8")
                    .body("data.name", is("Nostalgia"))
                    .body("data.title", is("My city"))
                    .body("data.size", is(1838920))
                    .body("data.type", is("image/jpeg"))
                    .log()
                    .all()
                    .when()
                    .post("upload")
                    .body()
                    .jsonPath()
                    .getString("data.id");;
        }

        /* Мои тести */

        @DisplayName("Тест загрузки текстового файла вместо картинки")
        @Test
        void postImageBadRequestTest() {
            given()
                    .spec(requestSpecification)
                    .multiPart("image", getFileResource("Lesson4_text.txt"))
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

        @DisplayName("Тест загрузки картинки в общую галерею")
        @Test
        @Order(3)
        void postGalleryImage() {
            given()
                    .spec(requestSpecification)
                    .formParam("title", "My city")
                    .log()
                    .all()
                    .expect()
                    .spec(responseSpecification)
                    .log()
                    .all()
                    .when()
                    .post("gallery/" + "image/" + CURRENT_IMAGE_ID);
    }

        @DisplayName("Тест создания комментария")
        @Test
        @Order(4)
        void createImageComment() {
            given()
                    .spec(requestSpecification)
                    .formParam("image_id", "CURRENT_IMAGE_ID")
                    .formParam("comment", "Magic")
                    .log()
                    .all()
                    .expect()
                    .log()
                    .all()
                    .when()
                    .post("comment");
        }

        @DisplayName("Тест запроса на картинку по номеру хэша")
        @Test
        @Order(5)
        void getImageHash(){
            given()
                    .spec(requestSpecification)
                    .log()
                    .all()
                    .expect()
                    .body("data.name", is("Nostalgia"))
                    .body("data.title", is("My city"))
                    .body("data.size", is(1838920))
                    .body("data.type", is("image/jpeg"))
                    .spec(responseSpecification)
                    .log()
                    .all()
                    .when()
                    .get("image/" + CURRENT_IMAGE_ID);
        }

        @DisplayName("Тест запроса всех постов из аккаунта")
        @Test
        @Order(6)
        void getAccountImage() {
            String userName = "irynapak";
            given()
                    .spec(requestSpecification)
                    .log()
                    .all()
                    .expect()
                    .spec(responseSpecification)
                    .log()
                    .all()
                    .when()
                    .get("account/" + "me/" + "images");
        }

        @DisplayName("Тест загрузки изменений title и description картинки")
        @Test
        @Order(7)
        void postUpdateImageInfo(){
            given()
                    .spec(requestSpecification)
                    .formParam("title", "Very interesting")
                    .formParam("description", "Nice to see! :-)")
                    .log()
                    .all()
                    .expect()
                    .spec(responseSpecification)
                    .log()
                    .all()
                    .when()
                    .post("image/" + CURRENT_IMAGE_ID);
        }

        @DisplayName("Тест добавления изображения в избранное")
        @Test
        @Order(8)
        void postImageToFavorite() {
            given()
                    .spec(requestSpecification)
                    .log()
                    .all()
                    .expect()
                    .spec(responseSpecification)
                    .body("data", is("favorited"))
                    .log()
                    .all()
                    .when()
                    .post("image/" + CURRENT_IMAGE_ID + "/favorite");
    }

        @DisplayName("Тест удаления картинки по номеру хэша")
        @Test
        @Order(9)
        void delImageHash(){
            given()
                    .spec(requestSpecification)
                    .log()
                    .all()
                    .expect()
                    .spec(responseSpecification)
                    .log()
                    .all()
                    .when()
                    .delete("image/" + CURRENT_IMAGE_ID);
        }

}
