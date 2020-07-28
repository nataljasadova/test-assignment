package tests;

import dataentities.PersonalDetails;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import logging.Log4jTestWatcher;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import java.time.LocalDateTime;
import java.time.Month;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PersonalDetailsTests {
    public static RequestSpecification requestSpec;

    public static String personId;

    @Rule
    public TestWatcher testWatcher = new Log4jTestWatcher();

    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(8080).
                build();
    }

    @Test
    public void requestPersonalDetails_checkResponseCode_expect200() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                then().
                assertThat().
                statusCode(200);

    }

    @Test
    public void requestPersonalDetails_checkResponseCode_expect404() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678913").
                then().
                assertThat().
                statusCode(404);
    }

    @Test
    public void requestPersonalDetails_checkResponseCode_expect500() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/1.2345678912").
                then().
                assertThat().
                statusCode(500);
    }


    @Test
    public void requestPersonalDetails_checkContentType_expectApplicationJson() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                then().
                assertThat().
                contentType(equalTo("application/json"));
    }

    @Test
    public void requestPersonalDetails_checkGender_expectMan() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                then().
                assertThat().
                body("gender", equalTo("Man"));
    }

    @Test
    public void requestPersonalDetails_checkBirthDay_expectBirthDay() {
        PersonalDetails personalDetails = given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                as(PersonalDetails.class);

        Assert.assertEquals(LocalDateTime.of(2000, Month.MARCH, 9, 17, 55), personalDetails.getBirthDay());
    }

    @Test
    public void requestPersonalDetails_checkPersonalDetails_expectPersonalDetailsObject() {
        PersonalDetails personalDetailsExpected = new PersonalDetails("1", "12345678912", "Pro", "John",
                LocalDateTime.of(2000, Month.MARCH, 9, 17, 55),
                null, "Man");

        PersonalDetails personalDetails = given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                as(PersonalDetails.class);

        Assert.assertEquals(personalDetailsExpected, personalDetails);
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
