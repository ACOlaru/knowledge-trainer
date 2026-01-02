package com.knowledgeTrainer.service.impl;

import com.knowledgeTrainer.domain.*;
import com.knowledgeTrainer.infrastructure.OpenAiClient;
import com.knowledgeTrainer.service.AiContentService;
import com.knowledgeTrainer.utils.ObjectMapperProvider;
import com.knowledgeTrainer.utils.PromptBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class OpenAiContentService implements AiContentService {

    private final OpenAiClient client;
    private final PromptBuilder promptBuilder;
    private final ObjectMapper mapper = ObjectMapperProvider.get();

    public OpenAiContentService(OpenAiClient openAiClient,
                                PromptBuilder promptBuilder) {
        this.client = openAiClient;
        this.promptBuilder = promptBuilder;
    }

    @Override
    public Optional<LearningContent> generateLearningContent(Topic topic) {
        try {
            String prompt = promptBuilder.learningPrompt(topic);
            String apiResponse = client.sendPrompt(prompt);

            String jsonContent = extractMessageContent(apiResponse);
            return parseLearningContent(jsonContent);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<LearningContent> parseLearningContent(String json) {
        try {
            return Optional.of(
                    mapper.readValue(json, LearningContent.class)
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse LearningContent",
                    e
            );
        }
    }

    @Override
    public Optional<PracticeSession> generateQuestions(
            Topic topic,
            Difficulty difficulty,
            int numberOfQuestions) {

        try {
            String prompt =
                    promptBuilder.practicePrompt(topic, difficulty, numberOfQuestions);

            String apiResponse = client.sendPrompt(prompt);
            String jsonContent = extractMessageContent(apiResponse);

            return Optional.of(
                    parsePracticeSession(jsonContent, topic)
            );

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private PracticeSession parsePracticeSession(
            String jsonContent,
            Topic topic) {

        try {
            JsonNode root = mapper.readTree(jsonContent);
            JsonNode questionsNode = root.get("questions");

            if (questionsNode == null || !questionsNode.isArray()) {
                throw new IllegalArgumentException(
                        "Invalid AI response: missing 'questions' array"
                );
            }

            List<Question> questions = new ArrayList<>();

            for (JsonNode qNode : questionsNode) {

                String questionText =
                        qNode.path("questionText").asText(null);

                JsonNode pointsNode =
                        qNode.path("expectedAnswerPoints");

                if (questionText == null || !pointsNode.isArray()) {
                    throw new IllegalArgumentException(
                            "Invalid question format in AI response"
                    );
                }

                List<String> expectedPoints = new ArrayList<>();
                for (JsonNode p : pointsNode) {
                    expectedPoints.add(p.asText());
                }

                questions.add(
                        new Question(questionText, expectedPoints)
                );
            }

            if (questions.isEmpty()) {
                throw new IllegalArgumentException(
                        "AI returned zero questions"
                );
            }

            return new PracticeSession(topic, questions, new HashMap<>(), new HashMap<>());

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse practice session",
                    e
            );
        }
    }

    @Override
    public Optional<Feedback> evaluateAnswer(
            Topic topic,
            Question question,
            Answer answer) {

        try {
            String prompt =
                    promptBuilder.evaluatePrompt(question, answer);

            String apiResponse = client.sendPrompt(prompt);
            String jsonContent = extractMessageContent(apiResponse);

            return parseFeedback(jsonContent);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<Feedback> parseFeedback(String json) {
        try {
            return Optional.of(
                    mapper.readValue(json, Feedback.class)
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse Feedback",
                    e
            );
        }
    }

    private String extractMessageContent(String apiResponse)
            throws Exception {

        JsonNode root = mapper.readTree(apiResponse);

        String content = root.path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText()
                .trim();

        if (content.startsWith("```")) {
            int firstNewline = content.indexOf('\n');
            int lastBacktick = content.lastIndexOf("```");

            if (firstNewline != -1 && lastBacktick != -1 && lastBacktick > firstNewline) {
                content = content.substring(firstNewline, lastBacktick).trim();
            }
        }

        content = content.replace("`", "");

        return content;

    }
}
