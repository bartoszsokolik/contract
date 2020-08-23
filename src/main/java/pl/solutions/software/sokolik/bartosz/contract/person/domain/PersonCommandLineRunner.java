package pl.solutions.software.sokolik.bartosz.contract.person.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PersonCommandLineRunner implements CommandLineRunner {

    private final PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {
        Person firstPerson = Person.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .build();

        Person secondPerson = Person.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        Person thirdPerson = Person.builder()
                .firstName("Piotr")
                .lastName("Kowalski")
                .build();

        log.info("Saving persons with id: {}, {}, {}", firstPerson.getId(), secondPerson.getId(), thirdPerson.getId());

        personRepository.saveAll(List.of(firstPerson, secondPerson, thirdPerson));
    }
}
