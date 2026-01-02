package com.knowledgeTrainer.infrastructure;

import com.knowledgeTrainer.utils.Config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenAiClient {

    private static final String API_URL =
            "https://api.openai.com/v1/chat/completions";

    private final HttpClient httpClient;
    private final String apiKey;
    private final String model;

    public OpenAiClient() {
        Config config = new Config();

        this.httpClient = HttpClient.newHttpClient();
        this.apiKey = config.getApiKey();
        this.model = config.getModel();

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "OPENAI_API_KEY environment variable not set"
            );
        }
    }

    public String sendPrompt(String prompt) throws Exception {
        String jsonBody = """
        {
          "model": "%s",
          "messages": [{"role": "user", "content": "%s"}]
        }
        """.formatted(model, escapeJson(prompt));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "OpenAI API error: " + response.body()
            );
        }


        return response.body();
    }

    private String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }
}

