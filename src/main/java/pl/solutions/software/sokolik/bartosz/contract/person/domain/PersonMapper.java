package pl.solutions.software.sokolik.bartosz.contract.person.domain;

import pl.solutions.software.sokolik.bartosz.contract.person.dto.PersonDto;

class PersonMapper {

    public Person map(PersonDto dto) {
        return Person.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .quote(dto.getQuote())
                .build();
    }
}
