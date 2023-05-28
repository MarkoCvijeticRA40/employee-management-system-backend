package com.mcm.EmployeeManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateTokensResponse {
    private String accessToken;
    private String refreshToken;
    private String roleName;
    private String email;
}
