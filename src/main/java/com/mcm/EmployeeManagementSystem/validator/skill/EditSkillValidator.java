package com.mcm.EmployeeManagementSystem.validator.skill;

import com.mcm.EmployeeManagementSystem.constant.SkillConstant;
import com.mcm.EmployeeManagementSystem.model.Skill;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
@RequiredArgsConstructor
public class EditSkillValidator implements Validator<Skill> {

    @Override
    public ValidationReport validate(Skill skill) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (skill == null) {
            report.setValid(false);
            report.addMessage(SkillConstant.SKILL, "skill is null");
        } else {
            if (isBlank(skill.getName())) {
                report.setValid(false);
                report.addMessage(SkillConstant.NAME, "name is blank");
            }
            if (skill.getLevel() == null) {
                report.setValid(false);
                report.addMessage(SkillConstant.LEVEL, "level is blank");
            }
            if (skill.getUser() == null) {
                report.setValid(false);
                report.addMessage(SkillConstant.USER, "user is blank");
            }
        }
        return report;
    }
}
