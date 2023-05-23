package com.mcm.EmployeeManagementSystem.converter;

import com.mcm.EmployeeManagementSystem.entity.TokenLinkEntity;
import com.mcm.EmployeeManagementSystem.model.TokenLink;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TokenLinkConverter implements GenericConverter<TokenLink, TokenLinkEntity> {
    @Override
    public TokenLink toModel(TokenLinkEntity tokenLinkEntity) {
        TokenLink tokenLink = new TokenLink();
        tokenLink.setId(tokenLinkEntity.getId());
        tokenLink.setValue(tokenLinkEntity.getValue());
        tokenLink.setIsUsed(tokenLinkEntity.getIsUsed());

        return tokenLink;
    }

    @Override
    public List<TokenLink> toModel(List<TokenLinkEntity> tokenLinkEntities) {
        List<TokenLink> tokenLinks = new ArrayList<>();
        for (TokenLinkEntity tokenLinkEntity : tokenLinkEntities) {
            tokenLinks.add(this.toModel(tokenLinkEntity));
        }

        return tokenLinks;
    }

    @Override
    public TokenLinkEntity toEntity(TokenLink tokenLink) {
        TokenLinkEntity tokenLinkEntity = new TokenLinkEntity();
        tokenLinkEntity.setId(tokenLink.getId());
        tokenLinkEntity.setValue(tokenLink.getValue());
        tokenLinkEntity.setIsUsed(tokenLink.getIsUsed());

        return tokenLinkEntity;
    }

    @Override
    public List<TokenLinkEntity> toEntity(List<TokenLink> tokenLinks) {
        List<TokenLinkEntity> tokenLinkEntities = new ArrayList<>();
        for (TokenLink tokenLink : tokenLinks) {
            tokenLinkEntities.add(this.toEntity(tokenLink));
        }

        return tokenLinkEntities;
    }
}
