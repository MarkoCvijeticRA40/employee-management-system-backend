package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.AddressConverter;
import com.mcm.EmployeeManagementSystem.model.Address;
import com.mcm.EmployeeManagementSystem.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddressStore {
    private final AddressRepository repository;
    private final AddressConverter converter;

    public Address find(Long id) {
        return converter.toModel(repository.findOne(id));
    }
}
