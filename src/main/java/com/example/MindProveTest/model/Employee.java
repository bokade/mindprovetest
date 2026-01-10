package com.example.MindProveTest.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "employees")
public class Employee {

    @Id
    private String id;
    private String name;
    private String department;
    private String email;
    private Integer salary;
    private Instant createdOn;
    private Instant modifiedOn;
    private Boolean isActive;

}
