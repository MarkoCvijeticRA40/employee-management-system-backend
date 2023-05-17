package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Address;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.usecase.CreateUserUseCase;
import com.mcm.EmployeeManagementSystem.usecase.UserSearchUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UserSearchUseCase searchUserUseCase;

    @GetMapping("/search/engineers/{email}/{name}/{surname}/{startDate}/{endDate}")
    public List<User> searchEngineers(
            @PathVariable String email,
            @PathVariable String name,
            @PathVariable String surname,
            @PathVariable String startDate,
            @PathVariable String endDate
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'X (zzzz)", Locale.ENGLISH);
        ZonedDateTime parsedStartDate = ZonedDateTime.parse(startDate, formatter);
        ZonedDateTime parsedEndDate = ZonedDateTime.parse(endDate, formatter);
        LocalDateTime localParsedStartDate = parsedStartDate.toLocalDateTime();
        LocalDateTime localParsedEndDate = parsedEndDate.toLocalDateTime();
        return searchUserUseCase.searchEngineers(email, name, surname, localParsedStartDate, localParsedEndDate);

    }
    @GetMapping("rolename")
    public List<User> findByRoleName(@RequestParam String roleName) {
        return searchUserUseCase.findByRoleName(roleName);
    }

    @GetMapping("title")
    public List<User> findByTitle(@RequestParam String title) {
        return searchUserUseCase.findByTitle(title);
    }

}
/*
//Funkcija za testiranje
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        Address address = new Address();
        address.setCity("Belgrade");
        address.setCountry("Serbia");
        address.setStreet("Slavija");
        address.setNumber("Trinaest");
        user.setEmail("dejanplayer@gmail.com");
        user.setPassword("Dejan123");
        user.setName("Dejan");
        user.setSurname("Milovanovic");
        user.setAddress(address);
        user.setPhoneNum("12412125125");
        user.setTitle("Engineer");
        List<String> roles = new ArrayList<>();
        String engineer = "Engineer";
        roles.add(engineer);
        user.setRoleNames(roles);
        LocalDateTime time = LocalDateTime.now();
        user.setStartOfWork(time);
        user.setAccountEnabled(true);
        LocalDateTime startTime = LocalDateTime.of(2021,06,4,5,40);
        LocalDateTime endTime = LocalDateTime.of(2022,06,4,5,40);
        List<User> users = searchUserUseCase.searchEngineers("dejanplayer@gmail.com","Dejan","Milovanovic",startTime,endTime);
        List<User> list = new ArrayList<>();
        return createUserUseCase.save(user);
    }
 */
