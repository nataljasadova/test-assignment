package dataentities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Person {
    public Person() {
    }

    public Person(String id, String personalCode, String lastName, String firstName, LocalDateTime birthDay, LocalDateTime deathDay, String gender) {
        this.id = id;
        this.personalCode = personalCode;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDay = birthDay;
        this.deathDay = deathDay;
        this.gender = gender;
    }

    private String id;

    private String personalCode;

    private String lastName;

    private String firstName;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthDay;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deathDay;

    private String gender;

}