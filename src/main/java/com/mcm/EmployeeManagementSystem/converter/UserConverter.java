package com.mcm.EmployeeManagementSystem.converter;

import com.mcm.EmployeeManagementSystem.entity.RoleEntity;
import com.mcm.EmployeeManagementSystem.entity.UserEntity;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.RoleRepository;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UserConverter implements GenericConverter<User, UserEntity> {
    private final AddressConverter addressConverter;
    private final RoleRepository roleRepository;

    @Override
    public User toModel(UserEntity userEntity) {
        if (userEntity != null) {
            User user = new User();
            user.setId(userEntity.getId());
            user.setEmail(userEntity.getEmail());
            user.setPassword(userEntity.getPassword());
            user.setName(userEntity.getName());
            user.setSurname(userEntity.getSurname());
            user.setAddress(addressConverter.toModel(userEntity.getAddress()));
            user.setPhoneNum(userEntity.getPhoneNum());
            user.setTitle(userEntity.getTitle());
            List<String> roleNames = new ArrayList<>();
            for (RoleEntity roleEntity : userEntity.getRoles()) {
                roleNames.add(roleEntity.getName());
            }
            user.setRoleNames(roleNames);
            user.setStartOfWork(userEntity.getStartOfWork());
            user.setAccountEnabled(userEntity.isAccountEnabled());

            return user;
        } else {
            return null;
        }
    }

    @Override
    public List<User> toModel(List<UserEntity> userEntities) {
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            users.add(this.toModel(userEntity));
        }

        return users;
    }

    @Override
    public UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setName(user.getName());
        userEntity.setSurname(user.getSurname());
        userEntity.setAddress(addressConverter.toEntity(user.getAddress()));
        userEntity.setPhoneNum(user.getPhoneNum());
        userEntity.setTitle(user.getTitle());
        List<RoleEntity> roleEntities = new ArrayList<>();
        for (String roleName : user.getRoleNames()) {
            roleEntities.add(roleRepository.findByName(roleName));
        }
        userEntity.setRoles(roleEntities);
        userEntity.setStartOfWork(user.getStartOfWork());
        userEntity.setAccountEnabled(user.isAccountEnabled());

        return userEntity;
    }

    @Override
    public List<UserEntity> toEntity(List<User> users) {
        List<UserEntity> userEntities = new ArrayList<>();
        for (User user : users) {
            userEntities.add(this.toEntity(user));
        }

        return userEntities;
    }
}
