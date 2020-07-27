package tests;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PersonBankAccountDetailsTests extends PersonPersonalDetailsTests {
    @Test
    public void requestBankAccount_checkResponseCode_expect200() {

        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                statusCode(202);
    }

    @Test
    public void requestBankAccount_checkResponseCode_expect404() {

        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/2").
                then().
                assertThat().
                statusCode(404);
    }

    @Test
    public void requestBankAccount_checkContentType_expectApplicationJson() {

        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                contentType(equalTo("application/json"));
    }

    @Test
    public void requestBankAccount_checkAccountNumber_expectAccountNumber() {

        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                body("accountNumber", equalTo("EE12345678912345678912"));
    }

    @Test
    public void requestBankAccount_checkCurrency_expectCurrencySize() {

        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                body("currency", hasLength(3));
    }

    @Test
    public void updateBankAccount_checkAccountNumber_expectAccountNumber() {

        given().
                spec(requestSpec).
                when().
                put("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                body("accountNumber", equalTo("EE12345678912345678914"));
    }
}
