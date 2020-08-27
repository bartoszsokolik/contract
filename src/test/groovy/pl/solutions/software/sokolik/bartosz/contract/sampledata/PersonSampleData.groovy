package pl.solutions.software.sokolik.bartosz.contract.sampledata

import pl.solutions.software.sokolik.bartosz.contract.person.domain.Person
import pl.solutions.software.sokolik.bartosz.contract.person.dto.CreatePersonDto
import pl.solutions.software.sokolik.bartosz.contract.person.dto.PersonDto

trait PersonSampleData {

    Person savedPerson = Person.builder()
            .id(UUID.fromString('294c0951-b392-4fa9-9653-ad8f2b99b966'))
            .firstName("Jan")
            .lastName("Kowalski")
            .build()

    def savedPersonDto = PersonDto.builder()
            .id(UUID.fromString('294c0951-b392-4fa9-9653-ad8f2b99b966'))
            .firstName("Jan")
            .lastName("Kowalski")
            .build()

    def createPersonDto = CreatePersonDto.builder()
            .firstName('Jan')
            .lastName('Kowalski')
            .build()
}