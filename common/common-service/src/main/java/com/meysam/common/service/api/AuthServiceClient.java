package com.meysam.common.service.api;

import com.meysam.common.model.dto.ClientLoginRequestDto;
import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import com.meysam.common.service.impl.AuthServiceFallBackFactory;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-ws",fallbackFactory = AuthServiceFallBackFactory.class)
public interface AuthServiceClient {


    @PostMapping(value = "/auth-client/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity clientLogin(@RequestBody @Valid ClientLoginRequestDto loginRequestDto);

    @PostMapping(value = "/auth-user/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity userLogin(@RequestBody @Valid LoginRequestDto loginRequestDto);

    @PostMapping(value = "/auth-user/register",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity registerUser(@RequestBody @Valid RegisterUserRequestDto registerRequestDto);

    @PostMapping(value = "/auth-user/refresh-token",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity refreshToken();
}
