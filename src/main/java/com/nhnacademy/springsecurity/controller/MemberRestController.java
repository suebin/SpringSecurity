package com.nhnacademy.springsecurity.controller;

import com.nhnacademy.springsecurity.domain.MemberCreateRequest;
import com.nhnacademy.springsecurity.domain.MemberId;
import com.nhnacademy.springsecurity.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public MemberId createMember(@RequestBody MemberCreateRequest request) {
        return memberService.createMember(request);
    }

}
