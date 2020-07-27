package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import logging.Log4jTestWatcher;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PersonPersonalDetailsTests {
    public static RequestSpecification requestSpec;

    public static String personId;

    private static Logger log = Logger.getLogger(PersonPersonalDetailsTests.class);

    @Rule
    public TestWatcher watchman = new Log4jTestWatcher();

    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(8080).
                build();
    }

    @Test
    public void requestPersonalCode_checkResponseCode_expect200() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                then().
                assertThat().
                statusCode(202);

    }

    @Test
    public void requestPersonalCode_checkResponseCode_expect404() {

        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678913").
                then().
                assertThat().
                statusCode(404);
    }

    @Test
    public void requestPersonalCode_checkContentType_expectApplicationJson() {

        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                then().
                assertThat().
                contentType(equalTo("application/json"));
    }

    @Test
    public void requestPersonalCode_checkGender_expectMan() {

        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                then().
                assertThat().
                body("gender", equalTo("Man"));
    }

    @Test
    public void extractPersonIdFromResponse() {

        personId =

                given().
                        spec(requestSpec).
                        when().
                        get("/api/person/personal-code/12345678912").
                        then().
                        extract().
                        path("id");

        Assert.assertEquals("1", personId);
    }

}
