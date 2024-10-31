package com.meysam.backoffice.webapi.controller.members;


import com.meysam.backoffice.webapi.config.aspect.log.annotation.MethodLog;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.constants.SessionConstants;
import com.meysam.common.customsecurity.model.dto.AssignDirectPermissionDto;
import com.meysam.common.customsecurity.model.dto.AssignProfileRoleDto;
import com.meysam.common.customsecurity.model.dto.RestResponseDTO;
import com.meysam.common.customsecurity.service.api.*;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ProfileRoleService adminRoleService;
    private final RolePermissionService rolePermissionService;
    private final PermissionService permissionService;
    private final LocaleMessageSourceService messageSourceService;



    @MethodLog
    @PostMapping(value = "assign-profile-permission")
    @PreAuthorize("hasAnyAuthority('PERMISSION_PERMISSION_MANAGEMENT')")
    public ResponseEntity<RestResponseDTO<?>> assignDirectPermission(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple clientPrinciple, @RequestBody AssignDirectPermissionDto directPermissionDto) {
        adminPermissionService.assignPermissionsToProfile(directPermissionDto.getPermissions(),directPermissionDto.getUsername());
        return ResponseEntity.ok(RestResponseDTO.generate(false,0,messageSourceService.getMessage("PERMISSION_ASSIGNED_SUCCESSFULLY")));
    }

    @MethodLog
    @PostMapping(value = "assign-profile-role")
    @PreAuthorize("hasAnyAuthority('PERMISSION_ROLE_MANAGEMENT')")
    public ResponseEntity<RestResponseDTO<?>> assignRole(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple clientPrinciple, @RequestBody AssignProfileRoleDto assignProfileRoleDto) {
        adminRoleService.assignRolesToProfile(assignProfileRoleDto.getRoles(),assignProfileRoleDto.getUsername());
        return ResponseEntity.ok(RestResponseDTO.generate(false,0,messageSourceService.getMessage("ROLE_ASSIGNED_SUCCESSFULLY")));
    }

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
