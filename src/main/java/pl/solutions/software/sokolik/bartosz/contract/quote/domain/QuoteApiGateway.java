package pl.solutions.software.sokolik.bartosz.contract.quote.domain;

import io.vavr.Value;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Traversable;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import pl.solutions.software.sokolik.bartosz.contract.quote.dto.QuoteCategoryResponse;
import pl.solutions.software.sokolik.bartosz.contract.quote.dto.QuoteResponse;
import pl.solutions.software.sokolik.bartosz.quotes.model.QOD;
import retrofit2.Call;

@CacheConfig(cacheNames = "categories")
@RequiredArgsConstructor
public class QuoteApiGateway {
    private static final String DEFAULT_LANGUAGE = "en";
    private static final boolean DEFAULT_DETAILED = false;
    private static final String CATEGORIES_KEY = "categories";
    private static final String QUOTES_KEY = "quotes";

    private final QuoteApi quoteApi;

    @Cacheable(key = "'categories'")
    public List<String> getCategories() {
        return Try.of(() -> quoteApi.getCategories(DEFAULT_LANGUAGE, DEFAULT_DETAILED).execute())
                .flatMap(res -> res.isSuccessful() ? Try.success(res.body()) : Try.failure(new RuntimeException()))
                .toOption()
                .map(QuoteCategoryResponse::getContents)
                .flatMap(c -> c.get(CATEGORIES_KEY))
                .map(Map::keySet)
                .map(Value::toList)
                .getOrElse(List.empty());
    }

    public String getQuoteOfTheDayForCategory(String category) {
        return getQuoteForCall(quoteApi.getQuoteOfTheDayForCategory(DEFAULT_LANGUAGE, category));
    }

    public String getQuoteOfTheDay() {
        return getQuoteForCall(quoteApi.getQuoteOfTheDay(DEFAULT_LANGUAGE));
    }

    public String getQuoteForCall(Call<QuoteResponse> call) {
        return Try.of(call::execute)
                .flatMap(res -> res.isSuccessful() ? Try.success(res.body()) : Try.failure(new RuntimeException()))
                .toOption()
                .map(QuoteResponse::getContents)
                .flatMap(c -> c.get(QUOTES_KEY))
                .flatMap(Traversable::headOption)
                .map(QOD::getQuote)
                .getOrNull();
    }
}
