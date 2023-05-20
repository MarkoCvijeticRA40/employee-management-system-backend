package com.mcm.EmployeeManagementSystem.usecase.link;

import com.mcm.EmployeeManagementSystem.model.ActivationLink;
import com.mcm.EmployeeManagementSystem.store.ActivationLinkStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetLinkToUsedUseCase {
    private final ActivationLinkStore store;

    public void setToUsed(String value) {
        ActivationLink activationLink = store.find(value);
        activationLink.setIsUsed(true);
        store.save(activationLink);
    }
}
