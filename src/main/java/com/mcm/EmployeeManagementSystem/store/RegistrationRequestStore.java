package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.RegistrationRequestConverter;
import com.mcm.EmployeeManagementSystem.entity.RegistrationRequestEntityStatus;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequest;
import com.mcm.EmployeeManagementSystem.repository.RegistrationRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RegistrationRequestStore {
    private final RegistrationRequestRepository repository;
    private final RegistrationRequestConverter converter;

    public RegistrationRequest save(RegistrationRequest request) {
        return converter.toModel(repository.save(converter.toEntity(request)));
    }

    public List<RegistrationRequest> find(RegistrationRequestEntityStatus status) {
        return converter.toModel(repository.findByStatus(status));
    }

    public RegistrationRequest find(Long id) {
        return converter.toModel(repository.findOne(id));
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    public RegistrationRequest find(String email) {
        return converter.toModel(repository.findByEmail(email));
    }

}
