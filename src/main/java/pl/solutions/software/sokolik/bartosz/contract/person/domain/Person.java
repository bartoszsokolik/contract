package pl.solutions.software.sokolik.bartosz.contract.person.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Person {

    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private final String firstName;
    private final String lastName;

//    public static class PersonBuilder {
//        public PersonBuilder id(UUID id) {
//            this.id = id == null ? UUID.randomUUID() : id;
//            return this;
//        }
//    }
}
