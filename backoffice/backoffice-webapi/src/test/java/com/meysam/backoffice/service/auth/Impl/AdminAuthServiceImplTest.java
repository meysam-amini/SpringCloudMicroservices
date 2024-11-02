package com.meysam.backoffice.service.auth.Impl;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.dto.AdminLoginResponseDto;
import com.meysam.common.customsecurity.model.dto.RegisterAdminRequestDto;
import com.meysam.common.customsecurity.model.dto.RegisterUserDto;
import com.meysam.common.customsecurity.model.entity.Profile;
import com.meysam.common.customsecurity.model.entity.ProfileRole;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.service.api.ProfileRoleService;
import com.meysam.common.customsecurity.service.api.ProfileService;
import com.meysam.common.customsecurity.service.api.RoleService;
import com.meysam.common.customsecurity.service.api.PrincipleService;
import com.meysam.common.customsecurity.utils.JwtUtil;
import com.meysam.common.model.dto.LoginRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
class AdminAuthServiceImplTest {


    @Mock
    private LocaleMessageSourceService messageSourceService;

    @Mock
    private PrincipleService principleService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ProfileService profileService;

    @Mock
    private ProfileRoleService adminRoleService;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ProfileAuthServiceImpl profileAuthService;


    @BeforeAll
    static void beforeAll() {
        log.info("Running Unit Tests...");
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_ShouldReturnAdminLoginResponseDto() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("admin");
        loginRequestDto.setPassword("123");

        String expectedToken = "sample-token";
        Profile p = new Profile();
        p.setPassword("");
        p.setId(1L);
        when(principleService.getSecurityPrinciple(anyString())).thenReturn(createMockSecurityPrinciple());
        when(jwtUtil.doGenerateToken(anyMap(), anyString())).thenReturn(expectedToken);
        when(profileService.getProfileEntityByUsername(anyString())).thenReturn(p);
        when(passwordEncoder.matches(anyString(),anyString())).thenReturn(true);

        ResponseEntity<AdminLoginResponseDto> response = profileAuthService.login(loginRequestDto);

        assertNotNull(response);
        assertEquals(expectedToken, response.getBody().getToken());
    }

    @Test
    void logout_ShouldReturnLogoutSuccessMessage() {
        String username = "admin";
        when(principleService.removeSession(username)).thenReturn(true);
        when(messageSourceService.getMessage("LOG_OUT_SUCCESS")).thenReturn("Logout successful");

        ResponseEntity<String> response = profileAuthService.logout(username);

        assertEquals("Logout successful", response.getBody());
        verify(principleService).removeSession(username);
    }

    @Test
    void logout_ShouldReturnSessionExpiredMessage() {
        String username = "admin";
        when(principleService.removeSession(username)).thenReturn(false);
        when(messageSourceService.getMessage("SESSION_EXPIRED_ALREADY")).thenReturn("Session expired");

        ResponseEntity<String> response = profileAuthService.logout(username);

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

        Profile profile = new Profile();
        profile.setId(1L);
        when(roleService.findRoleByName("ADMIN")).thenReturn(role);
        when(profileService.addProfile(any())).thenReturn(profile);

        ResponseEntity<?> response = profileAuthService.register(requestDto);

        assertEquals("register successful for username: johndoe", response.getBody());
        verify(profileService).addProfile(any(RegisterUserDto.class));
        verify(adminRoleService).add(any(ProfileRole.class));
    }

    @Test
    void register_ShouldThrowBusinessExceptionOnFailure() {
        RegisterAdminRequestDto requestDto = new RegisterAdminRequestDto();
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setRoleName("ADMIN");

        when(roleService.findRoleByName("ADMIN")).thenThrow(new RuntimeException());
        when(messageSourceService.getMessage("REGISTER_ASMIN_FAILED")).thenReturn("Registration failed");

        BusinessException exception = assertThrows(BusinessException.class, () -> profileAuthService.register(requestDto));
        assertEquals("Registration failed", exception.getMessage());
        verify(messageSourceService).getMessage("REGISTER_ASMIN_FAILED");
    }

    private SecurityPrinciple createMockSecurityPrinciple() {
        return SecurityPrinciple.builder()
                .profileId(1L)
                .roles(Collections.singletonList("ADMIN"))
                .permissions(Collections.singletonList("READ_PRIVILEGES"))
                .build();
    }

}