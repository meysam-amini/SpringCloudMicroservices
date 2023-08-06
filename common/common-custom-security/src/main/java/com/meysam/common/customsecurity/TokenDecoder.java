package com.meysam.common.customsecurity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meysam.common.customsecurity.model.OauthExtractedTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

@Slf4j
public class TokenDecoder {

    public static Object decodeToken(String authorization) {
        String[] split_string = authorization.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = null;
        try {
            actualObj = mapper.readTree(body);
        } catch (JsonProcessingException e) {
            log.error("can't read the parsed token");
            return null;
        }
        JsonNode usernameJsonNode = actualObj.get("preferred_username");
        OauthExtractedTokenDto keycloakExtractedTokenDto = new OauthExtractedTokenDto();
        keycloakExtractedTokenDto.setClientId(usernameJsonNode.textValue());
        return keycloakExtractedTokenDto;
    }

}
