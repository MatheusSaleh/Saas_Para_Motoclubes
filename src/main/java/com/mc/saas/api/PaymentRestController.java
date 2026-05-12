package com.mc.saas.api;

import com.mc.saas.api.dto.PaymentRequest;
import com.mc.saas.api.dto.PaymentResponse;
import com.mc.saas.domain.financial.Payment;
import com.mc.saas.domain.financial.PaymentGateway;
import com.mc.saas.domain.member.Member;
import com.mc.saas.domain.member.MemberGateway;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentRestController {

    private final PaymentGateway paymentGateway;
    private final MemberGateway memberGateway;

    @GetMapping
    public List<PaymentResponse> list() {
        return paymentGateway.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public PaymentResponse get(@PathVariable Long id) {
        Payment p = paymentGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado: " + id));
        return toResponse(p);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse create(@Valid @RequestBody PaymentRequest request) {
        Member member = new Member(
                request.memberId(),
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
        Payment payment = Payment.create(
                member,
                request.paymentDate(),
                request.monthYearReference(),
                request.amount(),
                request.paymentMethod(),
                request.paymentStatus(),
                request.dueDate()
        );
        return toResponse(paymentGateway.save(payment));
    }

    @PutMapping("/{id}")
    public PaymentResponse update(@PathVariable Long id, @Valid @RequestBody PaymentRequest request) {
        Member member = new Member(
                request.memberId(),
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
        Payment payment = new Payment(
                id,
                member,
                request.paymentDate(),
                request.monthYearReference(),
                request.amount(),
                request.paymentMethod(),
                request.paymentStatus(),
                request.dueDate()
        );
        return toResponse(paymentGateway.update(payment));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Payment p = paymentGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado: " + id));
        paymentGateway.delete(p);
    }

    private PaymentResponse toResponse(Payment p) {
        Member member = memberGateway.findById(p.getMember().getId());
        String personName = member.getPeople() != null ? member.getPeople().getFullName() : null;
        return new PaymentResponse(
                p.getId(),
                p.getMember().getId(),
                personName,
                p.getPaymentDate(),
                p.getMonthYearReference(),
                p.getAmount(),
                p.getPaymentMethod(),
                p.getPaymentStatus(),
                p.getDueDate()
        );
    }
}
