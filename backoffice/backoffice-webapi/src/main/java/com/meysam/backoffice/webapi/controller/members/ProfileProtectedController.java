package com.meysam.backoffice.webapi.controller.members;

import com.meysam.common.customsecurity.model.dto.PermissionGroupDto;
import com.meysam.backoffice.service.auth.api.ProfileAuthService;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.constants.SessionConstants;
import com.meysam.common.customsecurity.model.dto.*;
import com.meysam.common.customsecurity.service.api.PermissionService;
import com.meysam.common.customsecurity.service.api.ProfilePermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileProtectedController {

    private final ProfileAuthService profileAuthService;
    private final PermissionService permissionService;
    private final ProfilePermissionService profilePermissionService;


    @PostMapping("logout")
    public ResponseEntity<String> logoutProfile(@SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple securityPrinciple) {
        return profileAuthService.logout(securityPrinciple.getUsername());
    }

    @GetMapping(value = "get-mapped-permissions-for-menu", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PermissionGroupDto>> getMappedPermissionsForMenu(@SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple securityPrinciple){
        return ResponseEntity.ok(profilePermissionService.getMappedPermissions(securityPrinciple.getProfileId(),true));
    }

    @GetMapping(value = "get-mapped-permissions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PermissionGroupDto>> getMappedPermissions(@SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple securityPrinciple){
        return ResponseEntity.ok(profilePermissionService.getMappedPermissions(securityPrinciple.getProfileId(),false));
    }

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('PERMISSION_REGISTER_NEW_ADMIN')")
    public ResponseEntity registerNewAdmin(@Valid @RequestBody RegisterAdminRequestDto registerAdminRequestDto) {
        return profileAuthService.register(registerAdminRequestDto);
    }

    @PostMapping(value = "add-permission", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('PERMISSION_ADD_NEW_PERMISSION')")
    public ResponseEntity addNewPermission(@Valid @RequestBody AddPermissionDto addPermissionDto) {
        return ResponseEntity.ok(permissionService.addPermission(addPermissionDto));
    }

}
