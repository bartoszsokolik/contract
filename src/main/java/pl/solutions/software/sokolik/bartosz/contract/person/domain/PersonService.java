package pl.solutions.software.sokolik.bartosz.contract.person.domain;

import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import pl.solutions.software.sokolik.bartosz.contract.person.dto.CreatePersonDto;
import pl.solutions.software.sokolik.bartosz.contract.person.dto.PersonDto;
import pl.solutions.software.sokolik.bartosz.contract.person.dto.UpdatePersonDto;
import pl.solutions.software.sokolik.bartosz.contract.quote.domain.QuoteService;

import java.util.UUID;

@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonDtoMapper personDtoMapper;
    private final PersonMapper personMapper;
    private final QuoteService quoteService;

    public PersonDto create(CreatePersonDto dto) {
        Person personToSave = Person.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();

        Person savedPerson = personRepository.save(personToSave);
        return personDtoMapper.map(savedPerson);
    }

    public PersonDto update(UUID id, UpdatePersonDto dto) {
        PersonDto personDto = find(id);
        personDto = personDto.toBuilder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();

        Person savedPerson = personRepository.save(personMapper.map(personDto));
        return personDtoMapper.map(savedPerson);
    }

    public PersonDto get(UUID id) {
        return find(id);
    }

    public List<PersonDto> findAll() {
        return personRepository.findAll()
                .map(personDtoMapper::map);
    }

    public void delete(UUID id) {
        personRepository.deleteById(id);
    }

    public PersonDto assignRandomQuote(UUID personId) {
        PersonDto personDto = find(personId);
        String quote = quoteService.getRandomCategory()
                .map(quoteService::getRandomQuoteForCategory)
                .getOrElse(quoteService::getRandomQuote);

        personDto = personDto.toBuilder()
                .quote(quote)
                .build();

        Person savedPerson = personRepository.save(personMapper.map(personDto));
        return personDtoMapper.map(savedPerson);
    }

    private PersonDto find(UUID id) {
        return personRepository.findById(id)
                .map(personDtoMapper::map)
                .getOrElseThrow(() -> new RuntimeException(String.format("Person with id: %s not found", id)));
    }

}
