package com.Me.ShiftBoard.Model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Employee")
@Data
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Transient
    public static final String SEQUENCE_NAME = "employee_sequence";

    @Id
    private long id;
    private String firstName;
    private String lastName;
    private long externalId;
    private String email;
    private String contactNumber;
    private Address address;
    private long departmentId;

    public final static List<String> properties = List.of("firstName","lastName","externalId","email","contactNumber","country","city","state","zipCode","departmentId");

}
