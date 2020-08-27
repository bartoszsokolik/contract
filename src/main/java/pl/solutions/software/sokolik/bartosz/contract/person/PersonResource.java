package pl.solutions.software.sokolik.bartosz.contract.person;

import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.solutions.software.sokolik.bartosz.contract.person.domain.PersonService;
import pl.solutions.software.sokolik.bartosz.contract.person.dto.CreatePersonDto;
import pl.solutions.software.sokolik.bartosz.contract.person.dto.PersonDto;
import pl.solutions.software.sokolik.bartosz.contract.person.dto.UpdatePersonDto;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static pl.solutions.software.sokolik.bartosz.contract.Endpoint.API_ROOT;

@RestController
@RequiredArgsConstructor
@RequestMapping(PersonResource.PERSON_URI)
public class PersonResource {
    static final String PERSON_URI = API_ROOT + "/persons";

    private final PersonService personService;

    @PostMapping
    ResponseEntity<PersonDto> create(@RequestBody CreatePersonDto dto) {
        return new ResponseEntity<>(personService.create(dto), OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<PersonDto> update(@PathVariable UUID id, @RequestBody UpdatePersonDto dto) {
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

    @PostMapping("/{id}/quote")
    ResponseEntity<PersonDto> assignRandomQuote(@PathVariable UUID id) {
        return ResponseEntity.ok(personService.assignRandomQuote(id));
    }

}
