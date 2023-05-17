package com.mcm.EmployeeManagementSystem.converter;

import com.mcm.EmployeeManagementSystem.entity.RegistrationRequestEntity;
import com.mcm.EmployeeManagementSystem.entity.RegistrationRequestEntityStatus;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequest;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequestStatus;
import com.mcm.EmployeeManagementSystem.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class RegistrationRequestConverter implements GenericConverter<RegistrationRequest, RegistrationRequestEntity> {
    private final AddressConverter addressConverter;
    private final RoleRepository roleRepository;

    @Override
    public RegistrationRequest toModel(RegistrationRequestEntity registrationRequestEntity) {
        if (registrationRequestEntity != null) {
            RegistrationRequest request = new RegistrationRequest();
            request.setId(registrationRequestEntity.getId());
            request.setEmail(registrationRequestEntity.getEmail());
            request.setPassword(registrationRequestEntity.getPassword());
            request.setName(registrationRequestEntity.getName());
            request.setSurname(registrationRequestEntity.getSurname());
            request.setAddress(addressConverter.toModel(registrationRequestEntity.getAddress()));
            request.setPhoneNum(registrationRequestEntity.getPhoneNum());
            request.setTitle(registrationRequestEntity.getTitle());
            request.setRoleName(registrationRequestEntity.getRole().getName());
            request.setStatus(RegistrationRequestStatus.valueOf(registrationRequestEntity.getStatus().name()));
            request.setSendTime(registrationRequestEntity.getSendTime());

            return request;

        } else {
            return null;
        }
    }

    @Override
    public List<RegistrationRequest> toModel(List<RegistrationRequestEntity> registrationRequestEntities) {
        List<RegistrationRequest> registrationRequests = new ArrayList<>();
        for (RegistrationRequestEntity request : registrationRequestEntities) {
            registrationRequests.add(this.toModel(request));
        }

        return registrationRequests;
    }

    @Override
    public RegistrationRequestEntity toEntity(RegistrationRequest registrationRequest) {
        RegistrationRequestEntity request = new RegistrationRequestEntity();
        request.setId(registrationRequest.getId());
        request.setEmail(registrationRequest.getEmail());
        request.setPassword(registrationRequest.getPassword());
        request.setName(registrationRequest.getName());
        request.setSurname(registrationRequest.getSurname());
        request.setAddress(addressConverter.toEntity(registrationRequest.getAddress()));
        request.setPhoneNum(registrationRequest.getPhoneNum());
        request.setTitle(registrationRequest.getTitle());
        request.setRole(roleRepository.findByName(registrationRequest.getRoleName()));
        request.setStatus(RegistrationRequestEntityStatus.valueOf(registrationRequest.getStatus().name()));
        request.setSendTime(registrationRequest.getSendTime());

        return request;
    }

    @Override
    public List<RegistrationRequestEntity> toEntity(List<RegistrationRequest> registrationRequests) {
        List<RegistrationRequestEntity> registrationRequestEntities = new ArrayList<>();
        for (RegistrationRequest request : registrationRequests) {
            registrationRequestEntities.add(this.toEntity(request));
        }

        return registrationRequestEntities;
    }
}
