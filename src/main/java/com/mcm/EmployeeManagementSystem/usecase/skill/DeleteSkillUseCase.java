package com.mcm.EmployeeManagementSystem.usecase.skill;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Skill;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.SkillStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.skill.DeleteSkillValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteSkillUseCase {
    private final DeleteSkillValidator validator;
    private final SkillStore store;

    public Response delete(Long id) {
        ValidationReport report = validator.validate(id);
        Skill skill = new Skill();
        if (report.isValid()) {
            skill = store.find(id);
            store.delete(skill);
        }

        return new Response(report, skill.getId());
    }
}
