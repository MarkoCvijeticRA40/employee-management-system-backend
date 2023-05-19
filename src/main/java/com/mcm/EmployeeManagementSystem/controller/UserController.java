package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.usecase.CreateUserUseCase;
import com.mcm.EmployeeManagementSystem.usecase.UserSearchUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    private final UserStore store;

    @GetMapping("rolename")
    public List<User> findByRoleName(@RequestParam String roleName) {
        return searchUserUseCase.findByRoleName(roleName);
    }

    @GetMapping("title")
    public List<User> findByTitle(@RequestParam String title) {
        return searchUserUseCase.findByTitle(title);
    }

    @GetMapping("enabled")
    public List<User> getAllEnabled() {
        return store.getAllEnabled();
    }

    //Dobavljam samo potencijalne iznenjere i menadzere za neki projekat.Administratori i HR menadzeri ne rade direktno na projektu
    @GetMapping("potential/workers")
    public List<User> getAllPotentialWorkers() {
        return store.getAllPotentialWorkers();
    }

    @GetMapping("/{id}")
    public User getByEmail(@PathVariable int id) {
        Long Id = (long) id;
        User user = store.getById(Id);
        return store.getById(Id);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return store.save(user);
    }

    @PostMapping("/register/administrator")
    public User registerUser(@RequestBody User user) {
        user.setAccountEnabled(false);
        return createUserUseCase.save(user);
    }

    @GetMapping("/search/engineers/{email}/{name}/{surname}/{startDate}/{endDate}")
    public List<User> searchEngineers(@PathVariable String email, @PathVariable String name, @PathVariable String surname, @PathVariable String startDate, @PathVariable String endDate
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'X (zzzz)", Locale.ENGLISH);
        ZonedDateTime parsedStartDate = ZonedDateTime.parse(startDate, formatter);
        ZonedDateTime parsedEndDate = ZonedDateTime.parse(endDate, formatter);
        LocalDateTime localParsedStartDate = parsedStartDate.toLocalDateTime();
        LocalDateTime localParsedEndDate = parsedEndDate.toLocalDateTime();
        return searchUserUseCase.searchEngineers(email, name, surname, localParsedStartDate, localParsedEndDate);
    }
}

