package com.nhnacademy.springsecurity.repository;

import com.nhnacademy.springsecurity.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
