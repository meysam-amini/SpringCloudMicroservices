package com.meysam.backoffice.webapi.controller.members;

import com.meysam.backoffice.service.auth.api.AdminAuthService;
import com.meysam.common.model.dto.RegisterAdminRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
@RequiredArgsConstructor
public class AdminProtectedController {

    private final AdminAuthService adminAuthService;


    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('PERMISSION_regiter_new_admin')")
    public ResponseEntity registerNewAdmin(@Valid @RequestBody RegisterAdminRequestDto registerAdminRequestDto) {
        return adminAuthService.register(registerAdminRequestDto);
    }

}