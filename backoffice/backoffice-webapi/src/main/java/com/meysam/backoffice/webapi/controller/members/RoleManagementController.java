package com.meysam.backoffice.webapi.controller.members;

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
@PreAuthorize("hasAnyAuthority('PERMISSION_ROLE_MANAGEMENT')")
public class RoleManagementController {

    private final RoleService roleService;


    @GetMapping(value = "get-all-roles")
    public ResponseEntity<List<RoleDTO>> getRoles(@Parameter(hidden = true) @SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple clientPrinciple) {
        return ResponseEntity.ok(roleService.findAllRoles());
    }


    @PostMapping(value = "add-role", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewPermission(@Valid @RequestBody AddRoleDto addRoleDto) {
        return ResponseEntity.ok(roleService.addRole(addRoleDto));
    }
}
