package com.mc.saas.infrastructure.persistence.moto;

import com.mc.saas.domain.moto.Moto;
import com.mc.saas.domain.moto.MotoGateway;
import com.mc.saas.infrastructure.persistence.member.MemberJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MotoGatewayImpl implements MotoGateway {

    private final MotoJpaRepository motoRepository;
    private final MemberJpaRepository memberRepository;

    @Override
    @Transactional
    public Moto save(Moto moto) {
        MotoJpaEntity entity = new MotoJpaEntity();
        applyMember(moto, entity);
        copyScalars(moto, entity);
        return toDomain(motoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Moto findById(Long id) {
        return motoRepository.findById(id).map(this::toDomain)
                .orElseThrow(() -> new EntityNotFoundException("Moto não encontrada: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Moto> findAll() {
        return motoRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Moto> findOptionalById(Long id) {
        return motoRepository.findById(id).map(this::toDomain);
    }

    @Override
    @Transactional
    public void delete(Moto moto) {
        motoRepository.deleteById(moto.getId());
    }

    @Override
    @Transactional
    public Moto update(Moto moto) {
        MotoJpaEntity entity = motoRepository.findById(moto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Moto não encontrada: " + moto.getId()));
        applyMember(moto, entity);
        copyScalars(moto, entity);
        return toDomain(motoRepository.save(entity));
    }

    private void applyMember(Moto moto, MotoJpaEntity entity) {
        if (moto.getMemberId() == null) {
            throw new IllegalArgumentException("Moto deve referenciar um membro (memberId).");
        }
        entity.setMember(memberRepository.findById(moto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("Membro não encontrado: " + moto.getMemberId())));
    }

    private void copyScalars(Moto moto, MotoJpaEntity entity) {
        entity.setBrand(moto.getBrand());
        entity.setModel(moto.getModel());
        entity.setManufactureYear(moto.getManufactureYear());
        entity.setLicensePlate(moto.getLicensePlate());
        entity.setColor(moto.getColor());
        entity.setDisplacementCc(moto.getDisplacementCc());
        entity.setNotes(moto.getNotes());
    }

    private Moto toDomain(MotoJpaEntity e) {
        return new Moto(
                e.getId(),
                e.getMember().getId(),
                e.getBrand(),
                e.getModel(),
                e.getManufactureYear(),
                e.getLicensePlate(),
                e.getColor(),
                e.getDisplacementCc(),
                e.getNotes()
        );
    }
}
