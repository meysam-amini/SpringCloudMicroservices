package com.meysam.common.customsecurity.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientPrinciple {
    private String clientId;

    public static ClientPrinciple convertToClientPrinciple(OauthExtractedTokenDto keycloakExtractedTokenDto) {
        return ClientPrinciple.builder().clientId(keycloakExtractedTokenDto.getClientId()).build();
    }
}
