package com.meysam.backoffice.webapi.controller.members;


import com.meysam.backoffice.webapi.config.aspect.log.annotation.MethodLog;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.constants.SessionConstants;
import com.meysam.common.customsecurity.model.dto.*;
import com.meysam.common.customsecurity.service.api.*;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("role-permission")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('PERMISSION_PERMISSION_MANAGEMENT')")
public class PermissionManagementController {

    private final ProfileService adminService;
    private final RoleService roleService;
    private final ProfilePermissionService profilePermissionService;
    private final RolePermissionService rolePermissionService;
    private final PermissionService permissionService;
    private final LocaleMessageSourceService messageSourceService;

    @GetMapping(value = "get-role-permissions")
    public ResponseEntity<List<RolesPermissionsDTO>> getPermissionRoles(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple clientPrinciple) {
        return ResponseEntity.ok(profilePermissionService.getAllRolePermissions());
    }

    @GetMapping(value = "get-role-permissions-by-profile")
    public ResponseEntity<List<RolesPermissionsDTO>> getPermissionRolesByProfile(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple clientPrinciple, Long profile) {
        return ResponseEntity.ok(profilePermissionService.getAllRolePermissionsByProfile(profile));
    }

    @MethodLog
    @PostMapping(value = "assign-role-permission", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity assignRolePermission(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple clientPrinciple, @Valid @RequestBody AssignRolePermissionDto rolePermissionDto) {
        rolePermissionService.assignPermissionsToRole(clientPrinciple, rolePermissionDto);
        return ResponseEntity.ok(messageSourceService.getMessage("PERMISSION_ASSIGNED_TO_ROLE_SUCCESSFULLY"));
    }


    @GetMapping(value = "get-mapped-permissions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PermissionGroupDto>> getMappedPermissions(@SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple securityPrinciple){
        return ResponseEntity.ok(profilePermissionService.getMappedPermissions(securityPrinciple.getProfileId(),false));
    }


    @PostMapping(value = "add-permission", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewPermission(@Valid @RequestBody AddPermissionDto addPermissionDto) {
        return ResponseEntity.ok(permissionService.addPermission(addPermissionDto));
    }

}
