package com.mcm.EmployeeManagementSystem.validator.skill;

import com.mcm.EmployeeManagementSystem.constant.SkillConstant;
import com.mcm.EmployeeManagementSystem.constant.UserConstant;
import com.mcm.EmployeeManagementSystem.store.SkillStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class DeleteSkillValidator implements Validator<Long> {
    private final SkillStore store;

    @Override
    public ValidationReport validate(Long id) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (!store.exists(id)) {
            report.setValid(false);
            report.addMessage(SkillConstant.SKILL, "skill with passed id does not exist");
        }

        return report;
    }
}
