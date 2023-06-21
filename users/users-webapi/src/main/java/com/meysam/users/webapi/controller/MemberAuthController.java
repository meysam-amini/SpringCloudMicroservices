package com.meysam.users.webapi.controller;

import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import com.meysam.users.service.api.MemberService;
import jakarta.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/member")
public class MemberAuthController {

    private final Environment env;
    private final MemberService memberService;

    public MemberAuthController(Environment env, MemberService userService) {
        this.env = env;
        this.memberService = userService;
    }

    @PostMapping(value = "login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loginMember(@Valid @RequestBody LoginRequestDto loginRequestDto){
        return memberService.login(loginRequestDto);
    }

    @PostMapping(value = "register",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerMember(@Valid @RequestBody RegisterUserRequestDto registerUserRequestDto) {
        return memberService.register(registerUserRequestDto);
    }

    @GetMapping("all")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok().body(memberService.findAll());
    }



}
