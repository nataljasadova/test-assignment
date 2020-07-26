package dataentities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Person {
    private String id;

    private String personalCode;

    private String lastName;

    private String firstName;

    private LocalDateTime birthDay;

    private LocalDateTime deathDay;

    private String gender;

    private String accountNumber;

    private String currency;

    private String bankName;

}