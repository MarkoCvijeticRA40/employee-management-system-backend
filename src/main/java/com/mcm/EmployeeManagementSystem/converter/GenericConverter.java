package com.mcm.EmployeeManagementSystem.converter;

import java.util.List;

public interface GenericConverter<Model, Entity> {
    Model toModel(Entity entity);

    List<Model> toModel(List<Entity> entities);

    Entity toEntity(Model model);

    List<Entity> toEntity(List<Model> models);
}
