package pl.solutions.software.sokolik.bartosz.contract.person.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class PersonDto {

    private final UUID id;
    private final String firstName;
    private final String lastName;
}
