package pl.solutions.software.sokolik.bartosz.contract.person.domain

import pl.solutions.software.sokolik.bartosz.contract.sampledata.PersonSampleData
import pl.solutions.software.sokolik.bartosz.contract.quote.domain.QuoteApiGateway
import pl.solutions.software.sokolik.bartosz.contract.quote.domain.QuoteService
import spock.lang.Specification
import spock.lang.Subject

class PersonServiceSpec extends Specification implements PersonSampleData {

    PersonRepository personRepository = new InMemoryPersonRepository()
    PersonDtoMapper personDtoMapper = new PersonDtoMapper()
    PersonMapper personMapper = new PersonMapper()
    QuoteApiGateway quoteApiGateway = Mock()
    QuoteService quoteService = new QuoteService(new Random(), quoteApiGateway)

    @Subject
    PersonService personService = new PersonService(personRepository, personDtoMapper, personMapper, quoteService)

    def "should get person with given id"() {
        personRepository.save(savedPerson)

        when:
            def result = personService.get(savedPerson.getId())

        then:
            result == savedPersonDto
    }
}
