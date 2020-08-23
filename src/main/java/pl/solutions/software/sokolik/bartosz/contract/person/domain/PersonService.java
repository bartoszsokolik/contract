package pl.solutions.software.sokolik.bartosz.contract.person.domain;

import lombok.RequiredArgsConstructor;
import pl.solutions.software.sokolik.bartosz.contract.person.dto.CreatePersonDto;
import pl.solutions.software.sokolik.bartosz.contract.person.dto.PersonDto;
import pl.solutions.software.sokolik.bartosz.contract.person.dto.UpdatePersonDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonDtoMapper personDtoMapper;
    private final PersonMapper personMapper;

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
                .stream()
                .map(personDtoMapper::map)
                .collect(Collectors.toList());
    }

    private PersonDto find(UUID id) {
        return personRepository.findById(id)
                .map(personDtoMapper::map)
                .getOrElseThrow(() -> new RuntimeException(String.format("Person with id: %s not found", id)));
    }

    public void delete(UUID id) {
        personRepository.deleteById(id);
    }
}
