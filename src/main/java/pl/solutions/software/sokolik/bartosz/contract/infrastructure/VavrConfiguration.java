package pl.solutions.software.sokolik.bartosz.contract.infrastructure;

import io.vavr.jackson.datatype.VavrModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VavrConfiguration {

    @Bean
    VavrModule vavrModule() {
        return new VavrModule();
    }
}
