package com.meysam.backoffice.webapi.controller.members;


import com.meysam.backoffice.webapi.config.aspect.log.annotation.MethodLog;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.constants.SessionConstants;
import com.meysam.common.customsecurity.model.dto.AssignProfileRoleDto;
import com.meysam.common.customsecurity.model.dto.AssignRolePermissionDto;
import com.meysam.common.customsecurity.model.dto.RestResponseDTO;
import com.meysam.common.customsecurity.service.api.*;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("permission")
@RequiredArgsConstructor
public class PermissionManagementController {

    private final ProfileService adminService;
    private final RoleService roleService;
    private final ProfilePermissionService adminPermissionService;
    private final RolePermissionService rolePermissionService;
    private final PermissionService permissionService;
    private final LocaleMessageSourceService messageSourceService;



    @GetMapping(value = "get-all-roles")
    @PreAuthorize("hasAnyAuthority('PERMISSION_READ_ROLES')")
    public ResponseEntity<RestResponseDTO<?>> getRoles(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple clientPrinciple) {
        return ResponseEntity.ok(RestResponseDTO.generate(false,0,roleService.findAllRoles()));
    }

    @GetMapping(value = "get-role-permissions")
    @PreAuthorize("hasAnyAuthority('PERMISSION_READ_ROLES')")
    public ResponseEntity<RestResponseDTO<?>> getPermissionRoles(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple clientPrinciple) {
        return ResponseEntity.ok(RestResponseDTO.generate(false,0, adminPermissionService.getAllRolePermissions()));
    }

    @GetMapping(value = "get-role-permissions-by-profile")
    @PreAuthorize("hasAnyAuthority('PERMISSION_READ_ROLES')")
    public ResponseEntity<RestResponseDTO<?>> getPermissionRolesByProfile(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple clientPrinciple,Long profile) {
        return ResponseEntity.ok(RestResponseDTO.generate(false,0, adminPermissionService.getAllRolePermissionsByProfile(profile)));
    }


    @PostMapping(value = "assign-role-permission", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('PERMISSION_ADD_NEW_PERMISSION')")
    public ResponseEntity assignRolePermission(@Valid @RequestBody AssignRolePermissionDto rolePermissionDto) {
        rolePermissionService.assignPermissionsToRole(rolePermissionDto);
        return ResponseEntity.ok(messageSourceService.getMessage("PERMISSION_ASSIGNED_TO_ROLE_SUCCESSFULLY"));
    }

    /*@PostMapping(value = "assign-role-permission")
    @PreAuthorize("hasAnyAuthority('PERMISSION_ADD_NEW_PERMISSION')")
    public ResponseEntity assignRolePermission(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple clientPrinciple,@RequestBody AssignRolePermissionDto rolePermissionDto) {
        rolePermissionService.assignPermissionsToRole(rolePermissionDto);
        return ResponseEntity.ok(RestResponseDTO.generate(false,0,messageSourceService.getMessage("PERMISSION_ASSIGNED_SUCCESSFULLY")));
    }*/

    /*@PostMapping(value = "add-permission")
    @PreAuthorize("hasAnyAuthority('PERMISSION_ADD_NEW_PERMISSION')")
    public ResponseEntity<RestResponseDTO<?>> addNewPermission(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple clientPrinciple, @RequestBody AddPermissionDto addPermissionDto) {
        return ResponseEntity.ok(RestResponseDTO.generate(false,0,permissionService.addPermission(addPermissionDto)));
    }*/
}
