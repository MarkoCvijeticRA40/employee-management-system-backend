package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Address;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.usecase.hmac.hmacutil.VerifyHmacUseCase;
import com.mcm.EmployeeManagementSystem.usecase.link.IsActivationLinkUsedUseCase;
import com.mcm.EmployeeManagementSystem.usecase.link.SetLinkToUsedUseCase;
import com.mcm.EmployeeManagementSystem.usecase.user.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    private final VerifyHmacUseCase verifyHmacUseCase;
    private final ActivateAccountUseCase activateAccountUseCase;
    private final IsActivationLinkUsedUseCase isActivationLinkUsedUseCase;
    private final SetLinkToUsedUseCase setLinkToUsedUseCase;
    private final UserStore store;
    private final EditAdministratorProfileUseCase editAdministratorProfileUseCase;
    private final CreateAdministratorProfileUseCase createAdministratorProfileUseCase;
    private final FindByRoleNameUseCase findByRoleNameUseCase;
    private final SearchUsersUseCase searchUsersUseCase;
    private final FindByTitleUseCase findByTitleUseCase;
    private final GetAllEnabledUseCase getAllEnabledUseCase;
    private final FindPotentialEmployeeUseCase findPotentialEmployeeUseCase;
    private final FindUserUseCase findUserUseCase;
    private final EditEngineerUseCase editEngineerUseCase;
    private final DeleteEngineerUseCase deleteEngineerUseCase;
    private final EditProjectManagerUseCase editProjectManagerUseCase;

    private final PasswordEncoder passwordEncoder;
    private final FindUserByEmailUseCase findUserByEmailUseCase;

    @GetMapping("/activate")
    public String activateUser(@RequestParam("user") String userId,
                               @RequestParam("expires") String expirationString,
                               @RequestParam("hmac") String hmac) throws NoSuchAlgorithmException, InvalidKeyException {
        String dataToSign = userId + expirationString;

        if (verifyHmacUseCase.verify(dataToSign, hmac)) {
            LocalDateTime expirationDateTime = LocalDateTime.parse(expirationString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            if (LocalDateTime.now().isAfter(expirationDateTime)) {
                return "Activation link has expired.";
            }

            String activationLink = "https://localhost:443/users/activate?user=" + userId + "&expires=" + expirationString + "&hmac=" + hmac;
            if (!isActivationLinkUsedUseCase.isUsed(activationLink)) {
                activateAccountUseCase.activate(Long.valueOf(userId));
                setLinkToUsedUseCase.setToUsed(activationLink);
            } else {
                return "Activation link is already used";
            }
            return "User account activated successfully.";
        } else {
            return "Invalid activation link.";
        }
    }

    @GetMapping("rolename")
    public List<User> findByRoleName(@RequestParam String roleName) {
        return findByRoleNameUseCase.findByRoleName(roleName);
    }

    @GetMapping("title")
    public List<User> findByTitle(@RequestParam String title) {
        return findByTitleUseCase.findByTitle(title);
    }

    @GetMapping("enabled")
    public List<User> getAllEnabled() {
        return getAllEnabledUseCase.getAllEnabled();
    }

    //Dobavljam samo potencijalne iznenjere i menadzere za neki projekat.Administratori i HR menadzeri ne rade direktno na projektu
    @GetMapping("potential/workers")
    public List<User> getAllPotentialWorkers() {
        return findPotentialEmployeeUseCase.getAllPotentialWorkers();
    }

    @GetMapping("email/{id}")
    public User getByEmail(@PathVariable int id) {
        Long Id = (long) id;
        User user = store.getById(Id);
        return store.getById(Id);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return editAdministratorProfileUseCase.updateAdministrator(user);
    }

    @PostMapping("/register/administrator")
    public User registerUser(@RequestBody User user) {
        return createAdministratorProfileUseCase.register(user);
    }

    @GetMapping("/search/engineers/{email}/{name}/{surname}/{startDate}/{endDate}")
    public List<User> searchEngineers(@PathVariable String email, @PathVariable String name, @PathVariable String surname, @PathVariable String startDate, @PathVariable String endDate
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'X (zzzz)", Locale.ENGLISH);
        ZonedDateTime parsedStartDate = ZonedDateTime.parse(startDate, formatter);
        ZonedDateTime parsedEndDate = ZonedDateTime.parse(endDate, formatter);
        LocalDateTime localParsedStartDate = parsedStartDate.toLocalDateTime();
        LocalDateTime localParsedEndDate = parsedEndDate.toLocalDateTime();
        return searchUsersUseCase.searchEngineers(email, name, surname, localParsedStartDate, localParsedEndDate);
    }

    @GetMapping("/{id}/engineer")
    public Response findById(@PathVariable Long id) {
        return findUserUseCase.find(id);
    }

    @PutMapping("/{id}/engineer")
    public Response updateEngineer(@PathVariable Long id, @RequestBody User user) {
        return editEngineerUseCase.update(id, user);
    }

    @DeleteMapping("/{id}/engineer")
    public Response delete(@PathVariable Long id) {
        return deleteEngineerUseCase.delete(id);
    }

    @GetMapping("find/{email}")
    public Response user(@PathVariable String email) {
        return findUserByEmailUseCase.find(email);
    }
  
    @PutMapping("/{id}/project-manager")
    public Response updateProjectManager(@PathVariable Long id, @RequestBody User user) {
        return editProjectManagerUseCase.update(id, user);
    }
}

