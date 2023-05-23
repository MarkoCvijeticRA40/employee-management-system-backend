package com.mcm.EmployeeManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenLink {
    private Long id;
    private String value;
    private Boolean isUsed;
}
