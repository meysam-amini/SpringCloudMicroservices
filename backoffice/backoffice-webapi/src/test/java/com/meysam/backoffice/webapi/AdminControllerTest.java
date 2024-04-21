package com.meysam.backoffice.webapi;

import com.meysam.backoffice.service.auth.api.AdminAuthService;
import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.customsecurity.model.dto.AdminLoginResponseDto;
import com.meysam.common.customsecurity.service.api.PermissionService;
import com.meysam.common.model.dto.LoginRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private  AdminAuthService adminAuthService;
    @Mock
    private  PermissionService permissionService;

    static {
        GenericContainer<?> redis =
                new GenericContainer<>(DockerImageName.parse("redis:6")).withExposedPorts(6379);
        redis.start();
        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.port", redis.getMappedPort(6379).toString());
        System.setProperty("spring.data.redis.connect-timeout", "10000");
        System.setProperty("spring.data.redis.timeout", "1000");
        System.setProperty("spring.data.redis.jedis.pool.min-idle", "20");
        System.setProperty("spring.data.redis.jedis.pool.max-idle", "100");

    }

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
//            assertEquals(BusinessException.class,e);
            assertEquals("User not found", e.getMessage());
        }
    }

    public void logOutTest(){

    }

}
