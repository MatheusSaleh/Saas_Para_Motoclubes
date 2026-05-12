package com.mc.saas.api;

import com.mc.saas.api.dto.MotoRequest;
import com.mc.saas.api.dto.MotoResponse;
import com.mc.saas.domain.member.MemberGateway;
import com.mc.saas.domain.moto.Moto;
import com.mc.saas.domain.moto.MotoGateway;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motos")
@RequiredArgsConstructor
public class MotoRestController {

    private final MotoGateway motoGateway;
    private final MemberGateway memberGateway;

    @GetMapping
    public List<MotoResponse> list() {
        return motoGateway.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public MotoResponse get(@PathVariable Long id) {
        return toResponse(motoGateway.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MotoResponse create(@Valid @RequestBody MotoRequest request) {
        Moto moto = Moto.create(
                request.memberId(),
                request.brand().trim(),
                request.model().trim(),
                request.manufactureYear(),
                blankToNull(request.licensePlate()),
                blankToNull(request.color()),
                request.displacementCc(),
                blankToNull(request.notes())
        );
        return toResponse(motoGateway.save(moto));
    }

    @PutMapping("/{id}")
    public MotoResponse update(@PathVariable Long id, @Valid @RequestBody MotoRequest request) {
        Moto moto = new Moto(
                id,
                request.memberId(),
                request.brand().trim(),
                request.model().trim(),
                request.manufactureYear(),
                blankToNull(request.licensePlate()),
                blankToNull(request.color()),
                request.displacementCc(),
                blankToNull(request.notes())
        );
        return toResponse(motoGateway.update(moto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Moto moto = motoGateway.findOptionalById(id)
                .orElseThrow(() -> new EntityNotFoundException("Moto não encontrada: " + id));
        motoGateway.delete(moto);
    }

    private static String blankToNull(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        return s.trim();
    }

    private MotoResponse toResponse(Moto m) {
        var member = memberGateway.findById(m.getMemberId());
        String personName = member.getPeople() != null ? member.getPeople().getFullName() : null;
        return new MotoResponse(
                m.getId(),
                m.getMemberId(),
                personName,
                m.getBrand(),
                m.getModel(),
                m.getManufactureYear(),
                m.getLicensePlate(),
                m.getColor(),
                m.getDisplacementCc(),
                m.getNotes()
        );
    }
}
