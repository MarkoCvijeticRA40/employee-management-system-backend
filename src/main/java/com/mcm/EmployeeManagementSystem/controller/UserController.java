package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.usecase.CreateUserUseCase;
import com.mcm.EmployeeManagementSystem.usecase.UserSearchUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UserSearchUseCase searchUserUseCase;

    @GetMapping("/search")
    public Response searchUsers(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String surname) {
        return searchUserUseCase.findUsers(email, name, surname);
    }



}
