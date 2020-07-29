package tests;

import dataentities.BankAccount;
import logging.Log4jTestWatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static tests.PersonalDetailsTests.personId;
import static tests.PersonalDetailsTests.requestSpec;

public class BankAccountDetailsTests {

    @Rule
    public TestWatcher testWatcher = new Log4jTestWatcher();

    BankAccount bankAccount = new BankAccount("EE123456789123456788",
            "eur", "seb");


    @Test
    public void requestBankAccount_checkResponseCode_expect200() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void requestBankAccount_checkResponseCode_expect404() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/3").
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
                body("accountNumber", equalTo("EE123456789123456789"));
    }

    @Test
    public void requestBankAccount_checkAccountNumber_expectCurrencySize() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                body("accountNumber", hasLength(20));
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
    public void requestBankAccount_checkBankName_expectBankName() {
        BankAccount bankAccount = given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                as(BankAccount.class);

        Assert.assertEquals("seb", bankAccount.getBankName());
    }


    @Test
    public void updateBankAccount_checkAccountNumber_checkResponseCode_expect200() {
        given().
                spec(requestSpec).
                and().
                body(bankAccount).
                when().
                put("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void updateBankAccount_checkBankAccount_expectBankAccount() {
        given().
                spec(requestSpec).
                and().
                body(bankAccount).
                when().
                put("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                body("accountNumber", equalTo("EE123456789123456788")).
                and().
                body("currency", equalTo("eur")).
                and().
                body("bankName", equalTo("seb"));
    }


    @Test
    public void updateBankAccountWithoutPerson_checkResponseCode_expect404() {
        given().
                spec(requestSpec).
                and().
                body(bankAccount).
                when().
                put("/api/person/bank-account/person-id/3").
                then().
                assertThat().
                statusCode(404);
    }

    @Test
    public void updateBankAccountWithoutCurrencyAndBankName_checkResponseCode_expect500() {
        bankAccount.setBankName(null);
        bankAccount.setCurrency(null);
        given().
                spec(requestSpec).
                and().
                body(bankAccount).
                when().
                put("/api/person/bank-account/person-id/1").
                then().
                assertThat().
                statusCode(500);
    }
}
