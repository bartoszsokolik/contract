package pl.solutions.software.sokolik.bartosz.contract.person.domain;

import pl.solutions.software.sokolik.bartosz.contract.person.dto.PersonDto;

class PersonDtoMapper {

    public PersonDto map(Person person) {
        return PersonDto.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .build();
    }
}
