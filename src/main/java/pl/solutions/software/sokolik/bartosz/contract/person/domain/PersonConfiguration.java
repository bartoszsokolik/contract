package pl.solutions.software.sokolik.bartosz.contract.person.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.solutions.software.sokolik.bartosz.contract.SpringProfile;
import pl.solutions.software.sokolik.bartosz.contract.quote.domain.QuoteService;

import static pl.solutions.software.sokolik.bartosz.contract.SpringProfile.SAMPLE_DATA;

@Configuration
class PersonConfiguration {

    @Bean
    PersonRepository personRepository() {
        return new InMemoryPersonRepository();
    }

    @Bean
    PersonService personService(PersonRepository personRepository, QuoteService quoteService) {
        return new PersonService(personRepository, new PersonDtoMapper(), new PersonMapper(), quoteService);
    }

    @Bean
    @Profile(SAMPLE_DATA)
    PersonCommandLineRunner personCommandLineRunner(PersonRepository personRepository) {
        return new PersonCommandLineRunner(personRepository);
    }
}
