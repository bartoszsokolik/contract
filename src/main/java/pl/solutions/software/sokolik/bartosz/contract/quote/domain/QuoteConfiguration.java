package pl.solutions.software.sokolik.bartosz.contract.quote.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
class QuoteConfiguration {

    @Bean
    QuoteService quoteService(QuoteApi quoteApi) {
        return new QuoteService(new Random(), new QuoteApiGateway(quoteApi));
    }
}
