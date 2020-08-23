package pl.solutions.software.sokolik.bartosz.contract.person.domain;

import io.vavr.control.Option;

import java.util.List;
import java.util.UUID;

interface PersonRepository {

    Person save(Person person);

    Option<Person> findById(UUID id);

    List<Person> findAll();

    void saveAll(Iterable<Person> persons);

    void deleteById(UUID id);
}
