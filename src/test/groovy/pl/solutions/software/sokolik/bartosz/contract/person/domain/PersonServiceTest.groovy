package pl.solutions.software.sokolik.bartosz.contract.person.domain

import pl.solutions.software.sokolik.bartosz.contract.person.PersonSampleData
import spock.lang.Specification
import spock.lang.Subject

class PersonServiceTest extends Specification implements PersonSampleData {

    PersonRepository personRepository = new InMemoryPersonRepository()
    PersonDtoMapper personDtoMapper = new PersonDtoMapper()
    PersonMapper personMapper = new PersonMapper()

    @Subject
    PersonService personService = new PersonService(personRepository, personDtoMapper, personMapper)

    def "should get person with given id"() {
        personRepository.save(savedPerson)

        when:
            def result = personService.get(savedPerson.getId())

        then:
            result == savedPersonDto
    }
}
