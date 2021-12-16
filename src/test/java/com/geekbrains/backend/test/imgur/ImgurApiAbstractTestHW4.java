package com.geekbrains.backend.test.imgur;

import com.geekbrains.backend.test.FunctionalTest;
import io.restassured.RestAssured;
import io.restassured.authentication.OAuth2Scheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.internal.AuthenticationSpecificationImpl;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.is;
import java.util.Properties;

public class ImgurApiAbstractTestHW4 extends FunctionalTest {

    /*protected - Доступ внутри папки и всем наследникам */
    protected static RequestSpecification requestSpecification;

    static {
        try {
            Properties properties = readProperties();
            RestAssured.baseURI = properties.getProperty("imgur-api-URL");
            String TOKEN = properties.getProperty("imgur-api-token");
            requestSpecification = new RequestSpecBuilder()
                    .setAuth(new OAuth2Scheme())
                    .build();
            AuthenticationSpecification auth = new AuthenticationSpecificationImpl(requestSpecification);
            requestSpecification = auth.oauth2(TOKEN);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* Спецификация ответа */
    protected static ResponseSpecification responseSpecification;

    static {
        try {
            responseSpecification = new ResponseSpecBuilder()
                    .expectContentType("application/json")
                    .expectBody("status", is(200))
                    .expectBody("success", is(true))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
