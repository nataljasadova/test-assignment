package tests;

import dataentities.BankAccount;
import config.Log4jTestWatcher;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static tests.PersonalDetailsTests.personId;
import static tests.PersonalDetailsTests.requestSpec;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BankAccountDetailsTests {

    @Rule
    public TestWatcher testWatcher = new Log4jTestWatcher();

    BankAccount bankAccount = new BankAccount("EE123456789123456788",
            "eur", "seb");


    @Test
    public void request1BankAccount_checkResponseCode_expect200() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void request2BankAccount_checkResponseCode_expect404() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/3").
                then().
                assertThat().
                statusCode(404);
    }

    @Test
    public void request3BankAccount_checkContentType_expectApplicationJson() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                contentType(equalTo("application/json"));
    }

    @Test
    public void request4BankAccount_checkAccountNumber_expectAccountNumber() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                body("accountNumber", equalTo("EE123456789123456789"));
    }

    @Test
    public void request5BankAccount_checkAccountNumber_expectAccountNumberSize() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                body("accountNumber", hasLength(20));
    }

    @Test
    public void request6BankAccount_checkCurrency_expectCurrencySize() {
        given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                then().
                assertThat().
                body("currency", hasLength(3));
    }

    @Test
    public void request7BankAccount_checkBankName_expectBankName() {
        BankAccount bankAccount = given().
                spec(requestSpec).
                when().
                get("/api/person/bank-account/person-id/" + personId).
                as(BankAccount.class);

        Assert.assertEquals("seb", bankAccount.getBankName());
    }


    @Test
    public void update1BankAccount_checkAccountNumber_checkResponseCode_expect200() {
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
    public void update2BankAccount_checkBankAccount_expectBankAccount() {
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
    public void update3NotExistingBankAccount_checkResponseCode_expect404() {
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
    public void update4BankAccountWithoutAccountNumber_checkResponseCode_expect500() {
        bankAccount.setAccountNumber(null);
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

    @Test
    public void update5BankAccountWithoutCurrencyAndBankName_checkResponseCode_expect500() {
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
