package com.meysam.common.service.api;

import com.meysam.common.model.dto.ClientLoginRequestDto;
import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import com.meysam.common.service.impl.AuthServiceFallBackFactory;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-ws",fallbackFactory = AuthServiceFallBackFactory.class)
public interface AuthServiceClient {


    @PostMapping("/auth-client/login")
    ResponseEntity clientLogin(@RequestBody @Valid ClientLoginRequestDto loginRequestDto);

    @PostMapping("/auth-user/login")
    ResponseEntity userLogin(@RequestBody @Valid LoginRequestDto loginRequestDto);

    @PostMapping("/auth-user/register")
    ResponseEntity registerUser(@RequestBody @Valid RegisterUserRequestDto registerRequestDto);

    @PostMapping("/auth-user/refresh-token")
    ResponseEntity refreshToken();
}
