package pl.solutions.software.sokolik.bartosz.contract.quote.domain;

import brave.http.HttpTracing;
import brave.okhttp3.TracingInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@Slf4j(topic = "retrofit")
class QuoteApiConfiguration {

    private final HttpTracing httpTracing;

    @Bean
    QuoteApi quoteApi(@Value("${quote.api.url}") String url,
                      @Value("${quote.api.timeout}") Duration readTimeout,
                      ObjectMapper objectMapper) {

        return builder(jacksonConverterFactory(objectMapper), url, readTimeout)
                .build()
                .create(QuoteApi.class);
    }

    private Retrofit.Builder builder(Converter.Factory converterFactory, String url, Duration readTimeout) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(converterFactory)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient(readTimeout));
    }

    private JacksonConverterFactory jacksonConverterFactory(ObjectMapper objectMapper) {
        return JacksonConverterFactory.create(objectMapper);
    }

    private OkHttpClient httpClient(Duration readTimeout) {
        return new OkHttpClient.Builder()
                .readTimeout(readTimeout.toMillis(), TimeUnit.SECONDS)
                .dispatcher(new Dispatcher(
                        httpTracing.tracing().currentTraceContext()
                                .executorService(new Dispatcher().executorService())
                ))
                .addInterceptor(loggingInterceptor())
                .addInterceptor(errorInterceptor())
                .addNetworkInterceptor(TracingInterceptor.create(httpTracing))
                .build();
    }

    private Interceptor loggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(log::info);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private Interceptor errorInterceptor() {
        return chain -> {
            Request request = chain.request();
            Response response = Try.of(() -> chain.proceed(request))
                    .getOrElseThrow(
                            t -> new RuntimeException(String.format("%s (%s)", t.getMessage(), request.toString()), t));

            if (response.code() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                throw new RuntimeException(String.format("Destination server error occurred when making request to %s",
                        request.toString()));
            }

            return response;
        };
    }
}
