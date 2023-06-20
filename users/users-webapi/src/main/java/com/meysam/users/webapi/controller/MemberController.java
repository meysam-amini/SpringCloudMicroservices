package com.meysam.users.webapi.controller;

import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import com.meysam.common.model.entity.Member;
import com.meysam.users.service.api.MemberService;
import jakarta.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class MemberController {

    private final Environment env;
    private final MemberService memberService;

    public MemberController(Environment env, MemberService userService) {
        this.env = env;
        this.memberService = userService;
    }

    @GetMapping("status/check")
    public String status() {
        return "Working On Port " + env.getProperty("local.server.port") + " token: " + env.getProperty("token.secret");
    }

    @PostMapping("login")
    public ResponseEntity loginMember(@Valid @RequestBody LoginRequestDto loginRequestDto){
        return memberService.login(loginRequestDto);
    }

    @PostMapping("register")
    public ResponseEntity registerMember(@Valid @RequestBody RegisterUserRequestDto registerUserRequestDto) {
        return memberService.register(registerUserRequestDto);
    }

    @GetMapping("all")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok().body(memberService.findAll());
    }


    @GetMapping(value = "wallets")
//    @PreAuthorize("principal == #userId")
//    @PostAuthorize("principal == returnObject.getBody().getUserId()")
    @PreAuthorize("hasAnyAuthority('ROLE_USER_LEVEL_1')")
    public ResponseEntity getUserWallets(JwtAuthenticationToken token) {
        return ResponseEntity.ok().body(memberService.getUserWallets(token.getName()));
    }
}
