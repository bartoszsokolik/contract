package pl.solutions.software.sokolik.bartosz.contract.person.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreatePersonDto {

    private final String firstName;
    private final String lastName;

}
