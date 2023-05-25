package com.mcm.EmployeeManagementSystem.usecase.skill;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Skill;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.SkillStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.skill.EditSkillValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditSkillUseCase {
    private final EditSkillValidator validator;
    private final SkillStore store;
    public Response update(Long id, Skill skill) {
        ValidationReport report = validator.validate(skill);
        Skill editedSkill = store.find(id);
        if (report.isValid()) {
            editedSkill = skill;
            editedSkill = store.save(editedSkill);
        }

        return new Response(report, editedSkill);
    }
}
