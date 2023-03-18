package me.kimsoar.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Employee implements Serializable {
    private int id;
    private int companyId;
    private String employeeName;
    private String employeeAddress;
}
