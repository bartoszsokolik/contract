package pl.solutions.software.sokolik.bartosz.contract.person.dto;

import lombok.Value;

@Value
public class CreatePersonDto {

    private final String firstName;
    private final String lastName;
}
