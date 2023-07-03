package com.mcm.EmployeeManagementSystem.converter;

import com.mcm.EmployeeManagementSystem.entity.ActivationLinkEntity;
import com.mcm.EmployeeManagementSystem.model.ActivationLink;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActivationLinkConverter implements GenericConverter<ActivationLink, ActivationLinkEntity> {
    @Override
    public ActivationLink toModel(ActivationLinkEntity activationLinkEntity) {
        ActivationLink activationLink = new ActivationLink();
        activationLink.setId(activationLinkEntity.getId());
        activationLink.setValue(activationLinkEntity.getValue());
        activationLink.setIsUsed(activationLinkEntity.getIsUsed());

        return activationLink;
    }

    @Override
    public List<ActivationLink> toModel(List<ActivationLinkEntity> activationLinkEntities) {
        List<ActivationLink> activationLinks = new ArrayList<>();
        for (ActivationLinkEntity activationLinkEntity : activationLinkEntities) {
            activationLinks.add(this.toModel(activationLinkEntity));
        }

        return activationLinks;
    }

    @Override
    public ActivationLinkEntity toEntity(ActivationLink activationLink) {
        ActivationLinkEntity activationLinkEntity = new ActivationLinkEntity();
        activationLinkEntity.setId(activationLink.getId());
        activationLinkEntity.setValue(activationLink.getValue());
        activationLinkEntity.setIsUsed(activationLink.getIsUsed());

        return activationLinkEntity;
    }

    @Override
    public List<ActivationLinkEntity> toEntity(List<ActivationLink> activationLinks) {
        List<ActivationLinkEntity> activationLinkEntities = new ArrayList<>();
        for (ActivationLink activationLink : activationLinks) {
            activationLinkEntities.add(this.toEntity(activationLink));
        }

        return activationLinkEntities;
    }
}
