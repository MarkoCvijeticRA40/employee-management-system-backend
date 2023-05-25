package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Skill;
import com.mcm.EmployeeManagementSystem.usecase.skill.CreateSkillUseCase;
import com.mcm.EmployeeManagementSystem.usecase.skill.DeleteSkillUseCase;
import com.mcm.EmployeeManagementSystem.usecase.skill.EditSkillUseCase;
import com.mcm.EmployeeManagementSystem.usecase.skill.FindByUserIdUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/skills")
public class SkillController {
    private final CreateSkillUseCase createSkillUseCase;
    private final DeleteSkillUseCase deleteSkillUseCase;
    private final EditSkillUseCase editSkillUseCase;
    private final FindByUserIdUseCase findByUserIdUseCase;

    @PostMapping("/user/{id}")
    public Response create(@PathVariable Long id, @RequestBody Skill skill) {
        return createSkillUseCase.create(id, skill);
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        return deleteSkillUseCase.delete(id);
    }

    @PutMapping("/{id}")
    public Response update(@PathVariable Long id, @RequestBody Skill skill) {
        return editSkillUseCase.update(id, skill);
    }

    @GetMapping("/user/{id}")
    public Response getSkillsByUserId(@PathVariable Long id) {
        return findByUserIdUseCase.findByUserId(id);
    }
}
