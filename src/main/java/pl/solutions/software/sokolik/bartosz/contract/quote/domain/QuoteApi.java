package pl.solutions.software.sokolik.bartosz.contract.quote.domain;

import pl.solutions.software.sokolik.bartosz.contract.quote.dto.QuoteCategoryResponse;
import pl.solutions.software.sokolik.bartosz.contract.quote.dto.QuoteResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface QuoteApi {

    @GET("qod/categories")
    Call<QuoteCategoryResponse> getCategories(@Query("language") String language, @Query("detailed") boolean detailed);

    @GET("qod")
    Call<QuoteResponse> getQuoteOfTheDayForCategory(@Query("language") String language, @Query("category") String category);

    @GET("qod")
    Call<QuoteResponse> getQuoteOfTheDay(@Query("language") String language);
}
