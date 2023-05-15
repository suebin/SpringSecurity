package com.nhnacademy.springsecurity.service;

import com.nhnacademy.springsecurity.entity.Member;
import com.nhnacademy.springsecurity.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        return new User(member.getId(), member.getPwd(),
            Collections.singleton(new SimpleGrantedAuthority(member.getAuthority().getAuthority())));
    }
}
