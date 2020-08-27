package pl.solutions.software.sokolik.bartosz.contract.quote.domain;

import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class QuoteService {

    private final Random random;
    private final QuoteApiGateway quoteApiGateway;

    public Option<String> getRandomCategory() {
        List<String> categories = quoteApiGateway.getCategories();
        return Option.of(categories)
                .filter(c -> !c.isEmpty())
                .map(c -> c.get(random.nextInt(categories.size())));
    }

    public String getRandomQuoteForCategory(String category) {
        return quoteApiGateway.getQuoteOfTheDayForCategory(category);
    }

    public String getRandomQuote() {
        return quoteApiGateway.getQuoteOfTheDay();
    }
}
