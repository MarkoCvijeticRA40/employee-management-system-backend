package com.mcm.EmployeeManagementSystem.usecase.skill;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Skill;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.SkillStore;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.FindUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindByUserIdUseCase {
    private final UserStore userStore;
    private final SkillStore skillStore;
    private final FindUserValidator validator;

    public Response findByUserId(Long userId) {
        ValidationReport report = validator.validate(userId);
        User user = userStore.find(userId);
        List<Skill> skills = new ArrayList<Skill>();
        if (report.isValid()) {
            skills = skillStore.findByUser(user);
        }

        return new Response(report, skills);
    }
}
