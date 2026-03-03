package com.mc.saas.domain.member;

import java.util.List;

public interface MemberGateway {

    Member save(Member member);

    Member findById(Long id);

    List<Member> findAll();

    void delete(Member member);

    Member update(Member member);
}
