package com.meysam.common.service.api;

import com.meysam.common.model.dto.ClientLoginRequestDto;
import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import com.meysam.common.service.impl.AuthServiceFallBackFactory;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wallet-ws",fallbackFactory = AuthServiceFallBackFactory.class)
public interface AuthServiceClient {


    @GetMapping("/auth-client/login")
    ResponseEntity clientLogin(@RequestBody @Valid ClientLoginRequestDto loginRequestDto);

    @GetMapping("/auth-user/login")
    ResponseEntity userLogin(@RequestBody @Valid LoginRequestDto loginRequestDto);

    @GetMapping("/auth-user/login")
    ResponseEntity registerUser(@RequestBody @Valid RegisterUserRequestDto registerRequestDto);

    @GetMapping("/auth-user/refresh-token")
    ResponseEntity refreshToken();
}
