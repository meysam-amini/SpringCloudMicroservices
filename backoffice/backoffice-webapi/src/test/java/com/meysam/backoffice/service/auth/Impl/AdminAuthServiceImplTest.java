package com.meysam.backoffice.service.auth.Impl;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.dto.AdminLoginResponseDto;
import com.meysam.common.customsecurity.model.dto.RegisterAdminRequestDto;
import com.meysam.common.customsecurity.model.entity.Admin;
import com.meysam.common.customsecurity.model.entity.AdminRole;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.service.AdminRoleService;
import com.meysam.common.customsecurity.service.AdminService;
import com.meysam.common.customsecurity.service.RoleService;
import com.meysam.common.customsecurity.service.api.PrincipleService;
import com.meysam.common.customsecurity.utils.JwtUtil;
import com.meysam.common.model.dto.LoginRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdminAuthServiceImplTest {


    @InjectMocks
    private AdminAuthServiceImpl adminAuthService;

    @Mock
    private LocaleMessageSourceService messageSourceService;

    @Mock
    private PrincipleService principleService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AdminService adminService;

    @Mock
    private AdminRoleService adminRoleService;

    @Mock
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_ShouldReturnAdminLoginResponseDto() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("admin");

        String expectedToken = "sample-token";
        when(principleService.getSecurityPrinciple(anyString())).thenReturn(createMockSecurityPrinciple());
        when(jwtUtil.doGenerateToken(anyMap(), anyString())).thenReturn(expectedToken);

        ResponseEntity<AdminLoginResponseDto> response = adminAuthService.login(loginRequestDto);

        assertNotNull(response);
        assertEquals(expectedToken, response.getBody().getToken());
    }

    @Test
    void logout_ShouldReturnLogoutSuccessMessage() {
        String username = "admin";
        when(principleService.removeSession(username)).thenReturn(true);
        when(messageSourceService.getMessage("LOG_OUT_SUCCESS")).thenReturn("Logout successful");

        ResponseEntity<String> response = adminAuthService.logout(username);

        assertEquals("Logout successful", response.getBody());
        verify(principleService).removeSession(username);
    }

    @Test
    void logout_ShouldReturnSessionExpiredMessage() {
        String username = "admin";
        when(principleService.removeSession(username)).thenReturn(false);
        when(messageSourceService.getMessage("SESSION_EXPIRED_ALREADY")).thenReturn("Session expired");

        ResponseEntity<String> response = adminAuthService.logout(username);

        assertEquals("Session expired", response.getBody());
    }

    @Test
    void register_ShouldRegisterAdminSuccessfully() {
        RegisterAdminRequestDto requestDto = new RegisterAdminRequestDto();
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setPassword("password");
        requestDto.setPhone("123456789");
        requestDto.setRoleName("ADMIN");
        requestDto.setUsername("johndoe");

        Role role = new Role();
        role.setId(1L);
        when(roleService.findRoleByName("ADMIN")).thenReturn(role);

        ResponseEntity<?> response = adminAuthService.register(requestDto);

        assertEquals("register successful for username: johndoe", response.getBody());
        verify(adminService).add(any(Admin.class));
        verify(adminRoleService).add(any(AdminRole.class));
    }

    @Test
    void register_ShouldThrowBusinessExceptionOnFailure() {
        RegisterAdminRequestDto requestDto = new RegisterAdminRequestDto();
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setRoleName("ADMIN");

        when(roleService.findRoleByName("ADMIN")).thenThrow(new RuntimeException());
        when(messageSourceService.getMessage("REGISTER_ASMIN_FAILED")).thenReturn("Registration failed");

        BusinessException exception = assertThrows(BusinessException.class, () -> adminAuthService.register(requestDto));
        assertEquals("Registration failed", exception.getMessage());
        verify(messageSourceService).getMessage("REGISTER_ASMIN_FAILED");
    }

    private SecurityPrinciple createMockSecurityPrinciple() {
        return SecurityPrinciple.builder()
                .adminId(1L)
                .roles(Collections.singletonList("ADMIN"))
                .permissions(Collections.singletonList("READ_PRIVILEGES"))
                .build();
    }

}