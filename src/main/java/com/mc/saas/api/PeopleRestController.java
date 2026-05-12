package com.mc.saas.api;

import com.mc.saas.api.dto.PeopleRequest;
import com.mc.saas.api.dto.PeopleResponse;
import com.mc.saas.domain.people.People;
import com.mc.saas.domain.people.PeopleGateway;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/people")
@RequiredArgsConstructor
public class PeopleRestController {

    private final PeopleGateway peopleGateway;

    @GetMapping
    public List<PeopleResponse> list() {
        return peopleGateway.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public PeopleResponse get(@PathVariable Long id) {
        return toResponse(peopleGateway.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PeopleResponse create(@Valid @RequestBody PeopleRequest request) {
        People created = peopleGateway.save(People.create(request.fullName(), request.photo()));
        return toResponse(created);
    }

    @PutMapping("/{id}")
    public PeopleResponse update(@PathVariable Long id, @Valid @RequestBody PeopleRequest request) {
        People updated = peopleGateway.update(new People(id, request.fullName(), request.photo()));
        return toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        peopleGateway.deleteById(id);
    }

    private PeopleResponse toResponse(People p) {
        return new PeopleResponse(p.getId(), p.getFullName(), p.getPhoto());
    }
}
