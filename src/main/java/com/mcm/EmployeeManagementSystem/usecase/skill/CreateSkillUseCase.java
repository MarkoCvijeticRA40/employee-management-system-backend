package com.mcm.EmployeeManagementSystem.usecase.skill;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Skill;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.SkillStore;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.skill.CreateSkillValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSkillUseCase {
    private final CreateSkillValidator validator;
    private final SkillStore skillStore;
    private final UserStore userStore;
    public Response create(Long userId, Skill skill) {
        User user = userStore.find(userId);
        skill.setUser(user);
        ValidationReport report = validator.validate(skill);
        Skill createdSkill = new Skill();
        if (report.isValid()) {
            createdSkill = skillStore.save(skill);
        }

        return new Response(report, createdSkill);
    }
}
