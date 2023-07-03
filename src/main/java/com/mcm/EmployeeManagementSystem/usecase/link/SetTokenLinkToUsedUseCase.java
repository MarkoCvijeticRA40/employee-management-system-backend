package com.mcm.EmployeeManagementSystem.usecase.link;

import com.mcm.EmployeeManagementSystem.model.TokenLink;
import com.mcm.EmployeeManagementSystem.store.TokenLinkStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetTokenLinkToUsedUseCase {
    private final TokenLinkStore store;

    public void setToUsed(String value) {
        TokenLink tokenLink = store.find(value);
        tokenLink.setIsUsed(true);
        store.save(tokenLink);
    }
}
