package pl.solutions.software.sokolik.bartosz.contract.quote.dto;

import io.vavr.collection.List;
import io.vavr.collection.Map;
import lombok.Builder;
import lombok.Value;
import pl.solutions.software.sokolik.bartosz.quotes.model.QOD;

@Value
@Builder
public class QuoteResponse {

    private final Map<String, Integer> success;
    private final Map<String, List<QOD>> contents;
}
