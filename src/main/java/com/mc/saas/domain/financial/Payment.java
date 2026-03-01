package com.mc.saas.domain.financial;

import com.mc.saas.domain.member.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode(of = "id")
public class Payment {

    private Long id;

    private Member member;

    private LocalDate paymentDate;

    private String MonthYearReference;

    private Double amount;

    private String paymentMethod;

    private String paymentStatus;

    private LocalDate dueDate;

    public Payment(Long id, Member member, LocalDate paymentDate, String monthYearReference, Double amount, String paymentMethod, String paymentStatus, LocalDate dueDate) {
        this.id = id;
        this.member = member;
        this.paymentDate = paymentDate;
        MonthYearReference = monthYearReference;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.dueDate = dueDate;
    }

    public static Payment create(Member member, LocalDate paymentDate, String monthYearReference, Double amount, String paymentMethod, String paymentStatus, LocalDate dueDate) {
        return new Payment(null, member, paymentDate, monthYearReference, amount, paymentMethod, paymentStatus, dueDate);
    }
}
