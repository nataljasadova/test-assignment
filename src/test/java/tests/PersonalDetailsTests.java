package tests;

import dataentities.Person;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import config.Log4jTestWatcher;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runners.MethodSorters;

import java.time.LocalDateTime;
import java.time.Month;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void request1PersonalDetails_checkResponseCode_expect200() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                then().
                assertThat().
                statusCode(200);

    }

    @Test
    public void request2PersonalDetails_checkResponseCode_expect404() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678913").
                then().
                assertThat().
                statusCode(404);
    }

    @Test
    public void request3PersonalDetails_checkResponseCode_expect500() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/1.2345678912").
                then().
                assertThat().
                statusCode(500);
    }


    @Test
    public void request4PersonalDetails_checkContentType_expectApplicationJson() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                then().
                assertThat().
                contentType(equalTo("application/json"));
    }

    @Test
    public void request5PersonalDetails_checkGender_expectMan() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                then().
                assertThat().
                body("gender", equalTo("Man"));
    }

    @Test
    public void request6PersonalDetails_checkBirthDay_expectBirthDay() {
        Person personalDetails = given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                as(Person.class);

        Assert.assertEquals(LocalDateTime.of(2000, Month.MARCH, 9, 17, 55), personalDetails.getBirthDay());
    }

    @Test
    public void request7PersonalDetails_checkDeathDay_expectDeathDay() {
        Person personalDetails = given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/15747475486").
                as(Person.class);

        Assert.assertEquals(LocalDateTime.of(2010, Month.JULY, 9, 18, 10, 02), personalDetails.getDeathDay());
    }

    @Test
    public void request8PersonalDetails_checkPersonalDetailsObject_expectPersonalDetailsObject() {
        Person personalDetailsExpected = new Person("1", "12345678912", "Pro", "John",
                LocalDateTime.of(2000, Month.MARCH, 9, 17, 55),
                null, "Man");

        Person personalDetails = given().
                spec(requestSpec).
                when().
                get("/api/person/personal-code/12345678912").
                as(Person.class);

        Assert.assertEquals(personalDetailsExpected, personalDetails);
    }

    @Test
    public void requestPersonalDetails_extractPersonIdFromResponse() {
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
