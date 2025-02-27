package com.meysam.backoffice.webapi.controller.members;

import com.meysam.backoffice.service.auth.api.CachedDataService;
import com.meysam.backoffice.service.auth.api.ProfileAuthService;
import com.meysam.backoffice.webapi.config.aspect.log.annotation.MethodLog;
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
@RequestMapping("/cache")
@RequiredArgsConstructor
public class CachedDataController {

    private final CachedDataService cachedDataService;


    @MethodLog
    @PostMapping("refresh-cache")
    public ResponseEntity<String> logoutProfile(@SessionAttribute(SessionConstants.CLIENT_SESSION) SecurityPrinciple securityPrinciple) {
        cachedDataService.refreshCache();
        return ResponseEntity.ok("Cached Data Refreshed Successfully");
    }

}
