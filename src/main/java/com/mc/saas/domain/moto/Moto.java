package com.mc.saas.domain.moto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class Moto {

    private Long id;

    private Long memberId;

    private String brand;

    private String model;

    private Integer manufactureYear;

    private String licensePlate;

    private String color;

    private Integer displacementCc;

    private String notes;

    public Moto(
            Long id,
            Long memberId,
            String brand,
            String model,
            Integer manufactureYear,
            String licensePlate,
            String color,
            Integer displacementCc,
            String notes
    ) {
        this.id = id;
        this.memberId = memberId;
        this.brand = brand;
        this.model = model;
        this.manufactureYear = manufactureYear;
        this.licensePlate = licensePlate;
        this.color = color;
        this.displacementCc = displacementCc;
        this.notes = notes;
    }

    public static Moto create(
            Long memberId,
            String brand,
            String model,
            Integer manufactureYear,
            String licensePlate,
            String color,
            Integer displacementCc,
            String notes
    ) {
        return new Moto(null, memberId, brand, model, manufactureYear, licensePlate, color, displacementCc, notes);
    }
}
