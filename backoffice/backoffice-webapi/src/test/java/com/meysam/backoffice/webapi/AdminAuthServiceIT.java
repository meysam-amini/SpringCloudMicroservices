package com.meysam.backoffice.webapi;

import com.meysam.backoffice.service.auth.api.AdminAuthService;
import com.meysam.common.customsecurity.model.dto.AdminLoginResponseDto;
import com.meysam.common.customsecurity.service.api.PermissionService;
import com.meysam.common.model.dto.LoginRequestDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminAuthServiceIT {

    @Autowired
    private  AdminAuthService adminAuthService;
    @Autowired
    private  PermissionService permissionService;

    //postgres
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:14.2")
            .withExposedPorts(5432)
            .withUsername("admin")
            .withPassword("password");
    //redis
    static GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis:6")).withExposedPorts(6379);

    @BeforeAll
    static void beforeAll(){
        redis.start();
        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.port", redis.getMappedPort(6379).toString());
        System.setProperty("spring.data.redis.connect-timeout", "10000");
        System.setProperty("spring.data.redis.timeout", "1000");
        System.setProperty("spring.data.redis.jedis.pool.min-idle", "20");
        System.setProperty("spring.data.redis.jedis.pool.max-idle", "100");

        postgres.start();
        System.setProperty("spring.datasource.driverClassName", "org.postgresql.Driver");
        System.setProperty("spring.datasource.url",postgres.getJdbcUrl()); //"jdbc:postgresql://"+postgres.getHost()+":"+postgres.getMappedPort(5432).toString()+"/postgres");
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
        System.setProperty("spring.jpa.hibernate.ddl-auto", "update");
    }

    @AfterAll
    static void afterAll(){
        postgres.stop();
        redis.stop();
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
