package com.meysam.backoffice.webapi.controller.members;

import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.constants.SessionConstants;
import com.meysam.common.customsecurity.model.dto.ProfileDTO;
import com.meysam.common.customsecurity.model.dto.ProfileQueryModel;
import com.meysam.common.customsecurity.model.dto.RegisterUserDto;
import com.meysam.common.customsecurity.model.dto.RestResponseDTO;
import com.meysam.common.customsecurity.service.UserManagerService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagerService userManagerService;


    @PreAuthorize("hasAnyAuthority('PERMISSION_ADD_USER')")
    @PostMapping("add")
    public ResponseEntity<RestResponseDTO<?>> registerNewUser(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple securityPrinciple, @RequestBody RegisterUserDto registerUserDto){

        return ResponseEntity.ok(RestResponseDTO.generate(false,0,userManagerService.addUser(registerUserDto)));
    }

    @PreAuthorize("hasAnyAuthority('PERMISSION_QUERY_USER')")
    @PostMapping("query")
    public ResponseEntity<RestResponseDTO<Page<ProfileDTO>>> queryUsers(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple securityPrinciple, @RequestBody ProfileQueryModel profileQueryModel){

        return ResponseEntity.ok(RestResponseDTO.generate(false,0,userManagerService.queryUsers(profileQueryModel)));
    }

}
