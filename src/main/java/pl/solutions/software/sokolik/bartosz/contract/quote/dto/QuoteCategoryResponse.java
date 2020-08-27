package pl.solutions.software.sokolik.bartosz.contract.quote.dto;

import io.vavr.collection.Map;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QuoteCategoryResponse {

    private final Map<String, Integer> success;
    private final Map<String, Map<String, String>> contents;
}
