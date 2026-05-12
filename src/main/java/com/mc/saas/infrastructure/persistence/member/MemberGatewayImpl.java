package com.mc.saas.infrastructure.persistence.member;

import com.mc.saas.domain.member.Member;
import com.mc.saas.domain.member.MemberGateway;
import com.mc.saas.domain.people.People;
import com.mc.saas.infrastructure.persistence.people.PeopleJpaEntity;
import com.mc.saas.infrastructure.persistence.people.PeopleJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberGatewayImpl implements MemberGateway {

    private final MemberJpaRepository memberRepository;
    private final PeopleJpaRepository peopleRepository;

    @Override
    @Transactional
    public Member save(Member member) {
        MemberJpaEntity entity = new MemberJpaEntity();
        applyPeopleRefs(member, entity);
        copyScalars(member, entity);
        return toDomain(memberRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id).map(this::toDomain).orElseThrow(() -> new EntityNotFoundException("Membro não encontrado: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    @Transactional
    public void delete(Member member) {
        memberRepository.deleteById(member.getId());
    }

    @Override
    @Transactional
    public Member update(Member member) {
        MemberJpaEntity entity = memberRepository.findById(member.getId())
                .orElseThrow(() -> new EntityNotFoundException("Membro não encontrado: " + member.getId()));
        applyPeopleRefs(member, entity);
        copyScalars(member, entity);
        return toDomain(memberRepository.save(entity));
    }

    private void applyPeopleRefs(Member member, MemberJpaEntity entity) {
        if (member.getPeople() == null || member.getPeople().getId() == null) {
            throw new IllegalArgumentException("Membro deve referenciar uma pessoa (personId).");
        }
        entity.setPeople(peopleRepository.findById(member.getPeople().getId())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada: " + member.getPeople().getId())));
        if (member.getSpouse() != null && member.getSpouse().getId() != null) {
            entity.setSpouse(peopleRepository.findById(member.getSpouse().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Cônjuge não encontrado: " + member.getSpouse().getId())));
        } else {
            entity.setSpouse(null);
        }
    }

    private void copyScalars(Member member, MemberJpaEntity entity) {
        entity.setCpf(member.getCpf());
        entity.setRg(member.getRg());
        entity.setCountry(member.getCountry());
        entity.setOccupation(member.getOccupation());
        entity.setActive(member.isActive());
        entity.setUpToDate(member.isUpToDate());
        entity.setAlias(member.getAlias());
        entity.setBloodType(member.getBloodType());
        entity.setPhone(member.getPhone());
        entity.setEmail(member.getEmail());
        entity.setAuthorizedTheUseOfMedia(member.isAuthorizedTheUseOfMedia());
    }

    private Member toDomain(MemberJpaEntity e) {
        return new Member(
                e.getId(),
                toPeople(e.getPeople()),
                toPeople(e.getSpouse()),
                e.getCpf(),
                e.getRg(),
                e.getCountry(),
                e.getOccupation(),
                e.isActive(),
                e.isUpToDate(),
                e.getAlias(),
                e.getBloodType(),
                e.getPhone(),
                e.getEmail(),
                e.isAuthorizedTheUseOfMedia()
        );
    }

    private People toPeople(PeopleJpaEntity e) {
        if (e == null) {
            return null;
        }
        return new People(e.getId(), e.getFullName(), e.getPhoto());
    }
}
