package com.mc.saas.api;

import com.mc.saas.api.dto.MemberRequest;
import com.mc.saas.api.dto.MemberResponse;
import com.mc.saas.domain.member.Member;
import com.mc.saas.domain.member.MemberGateway;
import com.mc.saas.domain.people.People;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberGateway memberGateway;

    @GetMapping
    public List<MemberResponse> list() {
        return memberGateway.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public MemberResponse get(@PathVariable Long id) {
        return toResponse(memberGateway.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse create(@Valid @RequestBody MemberRequest request) {
        Member member = Member.create(
                new People(request.personId(), null, null),
                request.spouseId() != null ? new People(request.spouseId(), null, null) : null,
                request.cpf(),
                request.rg(),
                request.country(),
                request.occupation(),
                request.active(),
                request.upToDate(),
                request.alias(),
                request.bloodType(),
                request.phone(),
                request.email(),
                request.authorizedTheUseOfMedia()
        );
        return toResponse(memberGateway.save(member));
    }

    @PutMapping("/{id}")
    public MemberResponse update(@PathVariable Long id, @Valid @RequestBody MemberRequest request) {
        Member member = new Member(
                id,
                new People(request.personId(), null, null),
                request.spouseId() != null ? new People(request.spouseId(), null, null) : null,
                request.cpf(),
                request.rg(),
                request.country(),
                request.occupation(),
                request.active(),
                request.upToDate(),
                request.alias(),
                request.bloodType(),
                request.phone(),
                request.email(),
                request.authorizedTheUseOfMedia()
        );
        return toResponse(memberGateway.update(member));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        memberGateway.delete(memberGateway.findById(id));
    }

    private MemberResponse toResponse(Member m) {
        return new MemberResponse(
                m.getId(),
                m.getPeople() != null ? m.getPeople().getId() : null,
                m.getPeople() != null ? m.getPeople().getFullName() : null,
                m.getSpouse() != null ? m.getSpouse().getId() : null,
                m.getSpouse() != null ? m.getSpouse().getFullName() : null,
                m.getCpf(),
                m.getRg(),
                m.getCountry(),
                m.getOccupation(),
                m.isActive(),
                m.isUpToDate(),
                m.getAlias(),
                m.getBloodType(),
                m.getPhone(),
                m.getEmail(),
                m.isAuthorizedTheUseOfMedia()
        );
    }
}
