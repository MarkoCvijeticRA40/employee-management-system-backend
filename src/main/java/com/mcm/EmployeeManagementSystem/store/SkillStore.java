package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.SkillConverter;
import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.Skill;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SkillStore {
    private final SkillConverter converter;
    private final UserConverter userConverter;
    private final SkillRepository repository;

    public Skill save(Skill skill) {
        return converter.toModel(repository.save(converter.toEntity(skill)));
    }

    public void delete(Skill skill) {
        repository.delete(converter.toEntity(skill));
    }

    public Skill find(Long id) {
        return converter.toModel(repository.findOne(id));
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    public List<Skill> findByUser(User user) {
        return converter.toModel(repository.findByUser(userConverter.toEntity(user)));
    }
}
