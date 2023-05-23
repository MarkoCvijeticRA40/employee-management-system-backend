package com.mcm.EmployeeManagementSystem.usecase.link;

import com.mcm.EmployeeManagementSystem.model.TokenLink;
import com.mcm.EmployeeManagementSystem.store.TokenLinkStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IsTokenLinkUsedUseCase {
    private final TokenLinkStore store;

    public boolean isUsed(String value) {
        TokenLink tokenLink = store.find(value);
        return tokenLink.getIsUsed();
    }
}
