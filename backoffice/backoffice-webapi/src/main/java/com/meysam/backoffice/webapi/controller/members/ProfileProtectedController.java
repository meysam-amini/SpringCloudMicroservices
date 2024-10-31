package com.meysam.backoffice.webapi.controller.members;

import com.meysam.backoffice.service.auth.api.ProfileAuthService;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.constants.SessionConstants;
import com.meysam.common.customsecurity.model.dto.AssignDirectPermissionDto;
import com.meysam.common.customsecurity.model.dto.AssignRolePermissionDto;
import com.meysam.common.customsecurity.service.api.PermissionService;
import com.meysam.common.customsecurity.model.dto.AddPermissionDto;
import com.meysam.common.customsecurity.model.dto.RegisterAdminRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protected")
@RequiredArgsConstructor
public class ProfileProtectedController {

    private final ProfileAuthService profileAuthService;
    private final PermissionService permissionService;


    @PostMapping("log-out")
    public ResponseEntity<String> logoutAdmin(@SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple securityPrinciple) {
        return profileAuthService.logout(securityPrinciple.getUsername());
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

    @PostMapping(value = "assign-direct-permission", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('PERMISSION_ADD_NEW_PERMISSION')")
    public ResponseEntity assignDirectPermission(@Valid @RequestBody AssignDirectPermissionDto directPermissionDto) {
        return ResponseEntity.ok(permissionService.assignPermissionToUsername(directPermissionDto));
    }

    @PostMapping(value = "assign-role-permission", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('PERMISSION_ADD_NEW_PERMISSION')")
    public ResponseEntity assignRolePermission(@Valid @RequestBody AssignRolePermissionDto rolePermissionDto) {
        return ResponseEntity.ok(permissionService.assignPermissionToRole(rolePermissionDto));
    }


}
