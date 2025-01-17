package com.sprinboot2025.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprinboot2025.demo.cache.AppCache;
import com.sprinboot2025.demo.utility.ApiResponse;
import com.sprinboot2025.demo.utility.SeriesResult;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Slf4j
@Service
public class FeatureDataService {

    @Autowired
    private AppCache appCache;

    private static final String API_HOST = "cricket-live-data.p.rapidapi.com";
    private static final String BASE_URL = "https://" + API_HOST;

    private final OkHttpClient httpClient;

    public FeatureDataService() {
        this.httpClient = new OkHttpClient.Builder()
                .callTimeout(java.time.Duration.ofSeconds(30)) // Set timeout
                .build();
    }

    public SeriesResult getFeaturesOfSeries() {
        // Fetch the API key from the cache inside the method
        String apiKey = appCache.APP_CACHE.get("cricket_api");
        if (apiKey == null || apiKey.isEmpty()) {
            log.error("API key is not available in the cache");
            throw new RuntimeException("API key not found in cache");
        }

        HttpUrl url = HttpUrl.parse(BASE_URL + "/fixtures")
                .newBuilder()
                .addQueryParameter("limit", "10")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", API_HOST)
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Request failed with status code: {}", response.code());
                throw new RuntimeException("Failed request: " + response.message());
            }

            // Parse the response
            String responseBody = response.body() != null ? response.body().string() : null;
            if (responseBody == null || responseBody.isEmpty()) {
                log.warn("Empty response body");
                return new SeriesResult(); // Return an empty SeriesResult
            }

            // Deserialize the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseBody, SeriesResult.class);
        } catch (IOException e) {
            log.error("Error while making HTTP request: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching series data", e);
        }
    }
}
