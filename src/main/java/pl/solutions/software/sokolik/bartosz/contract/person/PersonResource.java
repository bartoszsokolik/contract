package pl.solutions.software.sokolik.bartosz.contract.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.solutions.software.sokolik.bartosz.contract.person.domain.PersonService;
import pl.solutions.software.sokolik.bartosz.contract.person.dto.CreatePersonDto;
import pl.solutions.software.sokolik.bartosz.contract.person.dto.PersonDto;
import pl.solutions.software.sokolik.bartosz.contract.person.dto.UpdatePersonDto;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(PersonResource.PERSON_URI)
public class PersonResource {
    static final String PERSON_URI = "/api/persons";

    private final PersonService personService;

    @PostMapping
    ResponseEntity<PersonDto> create(CreatePersonDto dto) {
        return new ResponseEntity<>(personService.create(dto), OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<PersonDto> update(@PathVariable UUID id, UpdatePersonDto dto) {
        return ResponseEntity.ok(personService.update(id, dto));
    }

    @GetMapping("/{id}")
    ResponseEntity<PersonDto> get(@PathVariable UUID id) {
        return ResponseEntity.ok(personService.get(id));
    }

    @GetMapping
    ResponseEntity<List<PersonDto>> getPersons() {
        return ResponseEntity.ok(personService.findAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable UUID id) {
        personService.delete(id);
    }
}
