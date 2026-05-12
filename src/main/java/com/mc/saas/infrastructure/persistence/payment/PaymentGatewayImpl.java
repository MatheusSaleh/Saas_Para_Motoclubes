package com.mc.saas.infrastructure.persistence.payment;

import com.mc.saas.domain.financial.Payment;
import com.mc.saas.domain.financial.PaymentGateway;
import com.mc.saas.domain.member.Member;
import com.mc.saas.infrastructure.persistence.member.MemberJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway {

    private final PaymentJpaRepository paymentRepository;
    private final MemberJpaRepository memberRepository;

    @Override
    @Transactional
    public Payment save(Payment payment) {
        PaymentJpaEntity entity = new PaymentJpaEntity();
        applyMember(payment, entity);
        copyScalars(payment, entity);
        return toDomain(paymentRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> findAll() {
        return paymentRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id).map(this::toDomain);
    }

    @Override
    @Transactional
    public void delete(Payment payment) {
        paymentRepository.deleteById(payment.getId());
    }

    @Override
    @Transactional
    public Payment update(Payment payment) {
        PaymentJpaEntity entity = paymentRepository.findById(payment.getId())
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado: " + payment.getId()));
        applyMember(payment, entity);
        copyScalars(payment, entity);
        return toDomain(paymentRepository.save(entity));
    }

    private void applyMember(Payment payment, PaymentJpaEntity entity) {
        if (payment.getMember() == null || payment.getMember().getId() == null) {
            throw new IllegalArgumentException("Pagamento deve referenciar um membro (memberId).");
        }
        entity.setMember(memberRepository.findById(payment.getMember().getId())
                .orElseThrow(() -> new EntityNotFoundException("Membro não encontrado: " + payment.getMember().getId())));
    }

    private void copyScalars(Payment payment, PaymentJpaEntity entity) {
        entity.setPaymentDate(payment.getPaymentDate());
        entity.setMonthYearReference(payment.getMonthYearReference());
        entity.setAmount(payment.getAmount());
        entity.setPaymentMethod(payment.getPaymentMethod());
        entity.setPaymentStatus(payment.getPaymentStatus());
        entity.setDueDate(payment.getDueDate());
    }

    private Payment toDomain(PaymentJpaEntity e) {
        Member memberRef = new Member(
                e.getMember().getId(),
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                false,
                null,
                null,
                null,
                null,
                false
        );
        return new Payment(
                e.getId(),
                memberRef,
                e.getPaymentDate(),
                e.getMonthYearReference(),
                e.getAmount(),
                e.getPaymentMethod(),
                e.getPaymentStatus(),
                e.getDueDate()
        );
    }
}
