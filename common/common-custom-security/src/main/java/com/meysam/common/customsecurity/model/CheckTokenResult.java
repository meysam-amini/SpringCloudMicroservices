package com.meysam.common.customsecurity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder@NoArgsConstructor@AllArgsConstructor@Data
public class CheckTokenResult {
    private boolean active;
    private Long exp;
    @JsonProperty("user_name")
    private String username;
    @JsonProperty("client_id")
    private String clientId;

}
