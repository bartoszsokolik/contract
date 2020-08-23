package pl.solutions.software.sokolik.bartosz.contract.person.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PersonConfiguration {

    @Bean
    PersonRepository personRepository() {
        return new InMemoryPersonRepository();
    }

    @Bean
    PersonService personService(PersonRepository personRepository) {
        return new PersonService(personRepository, new PersonDtoMapper(), new PersonMapper());
    }

    @Bean
    PersonCommandLineRunner personCommandLineRunner(PersonRepository personRepository) {
        return new PersonCommandLineRunner(personRepository);
    }
}
