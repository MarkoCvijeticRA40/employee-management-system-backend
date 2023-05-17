package com.mcm.EmployeeManagementSystem.converter;

import com.mcm.EmployeeManagementSystem.entity.SkillEntity;
import com.mcm.EmployeeManagementSystem.model.Skill;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class SkillConverter implements GenericConverter<Skill, SkillEntity> {
    private final UserConverter userConverter;

    @Override
    public Skill toModel(SkillEntity skillEntity) {
        if (skillEntity != null) {
            Skill skill = new Skill();
            skill.setId(skillEntity.getId());
            skill.setName(skillEntity.getName());
            skill.setLevel(skillEntity.getLevel());
            skill.setUser(userConverter.toModel(skillEntity.getUser()));

            return skill;
        } else {
            return null;
        }
    }

    @Override
    public List<Skill> toModel(List<SkillEntity> skillEntities) {
        List<Skill> skills = new ArrayList<>();
        for (SkillEntity skillEntity : skillEntities) {
            skills.add(this.toModel(skillEntity));
        }

        return skills;
    }

    @Override
    public SkillEntity toEntity(Skill skill) {
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setId(skill.getId());
        skillEntity.setName(skill.getName());
        skillEntity.setLevel(skill.getLevel());
        skillEntity.setUser(userConverter.toEntity(skill.getUser()));

        return skillEntity;
    }

    @Override
    public List<SkillEntity> toEntity(List<Skill> skills) {
        List<SkillEntity> skillEntities = new ArrayList<>();
        for (Skill skill : skills) {
            skillEntities.add(this.toEntity(skill));
        }

        return skillEntities;
    }
}
