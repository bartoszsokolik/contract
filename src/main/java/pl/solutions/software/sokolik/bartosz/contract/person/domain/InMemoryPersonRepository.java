package pl.solutions.software.sokolik.bartosz.contract.person.domain;

import io.vavr.collection.List;
import io.vavr.control.Option;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryPersonRepository implements PersonRepository {

    private final Map<UUID, Person> repository = new ConcurrentHashMap<>();

    @Override
    public Person save(Person person) {
        repository.put(person.getId(), person);
        return person;
    }

    @Override
    public Option<Person> findById(UUID id) {
        return Option.of(repository.get(id));
    }

    @Override
    public List<Person> findAll() {
        return List.ofAll(repository.values());
    }

    @Override
    public void saveAll(Iterable<Person> persons) {
        persons.forEach(person -> repository.put(person.getId(), person));
    }

    @Override
    public void deleteById(UUID id) {
        repository.remove(id);
    }
}
