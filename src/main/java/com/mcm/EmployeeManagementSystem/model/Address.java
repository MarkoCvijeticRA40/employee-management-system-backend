package com.mcm.EmployeeManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Long id;
    private String country;
    private String city;
    private String street;
    private String number;
}
