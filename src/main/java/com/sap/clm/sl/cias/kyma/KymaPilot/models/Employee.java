package com.sap.clm.sl.cias.kyma.KymaPilot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name = "EMPLOYEE")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Employee {

    @Id
    private String id;

    private String name;

    private int age;
}
