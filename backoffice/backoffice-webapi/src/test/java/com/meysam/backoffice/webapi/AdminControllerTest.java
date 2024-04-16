package com.meysam.backoffice.webapi;

import com.meysam.backoffice.service.auth.api.AdminAuthService;
import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.customsecurity.model.dto.AdminLoginResponseDto;
import com.meysam.common.customsecurity.service.api.PermissionService;
import com.meysam.common.model.dto.LoginRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminControllerTest {

    @Autowired
    private  AdminAuthService adminAuthService;
    @Mock
    private  PermissionService permissionService;

    @Test
    public void loginTest(){
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username("user")
                .password("p")
                .build();
        try {
            ResponseEntity<AdminLoginResponseDto> response = adminAuthService.login(loginRequestDto);
            assertEquals(ResponseEntity.ok(),response.getStatusCode());

        }catch (Exception e){
            assertEquals(BusinessException.class,e.getCause().getClass());
        }
    }

    public void logOutTest(){

    }

}
