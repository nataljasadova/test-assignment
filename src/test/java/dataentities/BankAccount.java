package dataentities;

import lombok.Data;

@Data
public class BankAccount {

    public BankAccount() {
    }

    public BankAccount(String accountNumber, String currency, String bankName) {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.bankName = bankName;
    }

    private String accountNumber;

    private String currency;

    private String bankName;

}
