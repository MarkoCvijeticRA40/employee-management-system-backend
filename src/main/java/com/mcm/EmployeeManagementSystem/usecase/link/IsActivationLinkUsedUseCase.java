package com.mcm.EmployeeManagementSystem.usecase.link;

import com.mcm.EmployeeManagementSystem.model.ActivationLink;
import com.mcm.EmployeeManagementSystem.store.ActivationLinkStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IsActivationLinkUsedUseCase {
    private final ActivationLinkStore store;

    public boolean isUsed(String value) {
        ActivationLink activationLink = store.find(value);
        return activationLink.getIsUsed();
    }
}
