package com.meysam.backoffice.webapi.controller.members;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class MemberController {


    @GetMapping("test")
    public ResponseEntity<String> testBackofficeWebApi(){
        log.info("members/test ===> testBackofficeWebApi called at time : {}",System.currentTimeMillis());
        return ResponseEntity.ok("OKKKK");
    }
}
