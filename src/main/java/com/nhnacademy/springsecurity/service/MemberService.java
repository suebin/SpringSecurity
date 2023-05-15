package com.nhnacademy.springsecurity.service;

import com.nhnacademy.springsecurity.domain.MemberCreateRequest;
import com.nhnacademy.springsecurity.domain.MemberId;
import com.nhnacademy.springsecurity.entity.Authority;
import com.nhnacademy.springsecurity.entity.Member;
import com.nhnacademy.springsecurity.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public MemberId createMember(MemberCreateRequest request) {
        Member member = new Member();
        member.setId(request.getId());
        member.setName(request.getName());
        member.setPwd(passwordEncoder.encode(request.getPwd()));

        Authority authority = new Authority();
        authority.setMember(member);
        authority.setAuthority(request.getAuthority());

        member.setAuthority(authority);

        memberRepository.saveAndFlush(member);

        MemberId memberId = new MemberId();
        memberId.setId(member.getId());

        return memberId;
    }

}
