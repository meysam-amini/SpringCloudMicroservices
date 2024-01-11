package com.meysam.backoffice.webapi.controller.members;

import com.meysam.common.model.dto.MemberDto;
import com.meysam.common.model.dto.MemberFilterDto;
import com.meysam.common.service.api.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("members")
@RefreshScope
public class MemberController {

//    private final MemberService memberService;

    @Value("${test.refresh.bus:default}")
    private String testBusrefresh;

    @GetMapping("test")
    public ResponseEntity<String> testBackofficeWebApi(){
        log.info("members/test ===> testBackofficeWebApi called at time : {}",System.currentTimeMillis());
        log.error("testBusRefresh : "+testBusrefresh);
        return ResponseEntity.ok("OK: "+testBusrefresh );
    }

    @PostMapping("page-query")
    public ResponseEntity<Page<MemberDto>> pageQuery(MemberFilterDto memberFilterDto){
//        return ResponseEntity.ok(memberService.pageQuery(memberFilterDto));
        return null;
    }
}
