package com.knowledgeTrainer.utils;

import com.knowledgeTrainer.domain.Answer;
import com.knowledgeTrainer.domain.Difficulty;
import com.knowledgeTrainer.domain.Question;
import com.knowledgeTrainer.domain.Topic;

public class PromptBuilder {

    /**
     * Build a prompt for generating learning content for a topic
     */
    public String learningPrompt(Topic topic) {
        String topicName = topic.name().replace("\"", "\\\"");
        String description = topic.description() == null ? "" : topic.description().replace("\"", "\\\"");

        String template = "Generate a concise learning summary for the following topic:\n\n" +
                "Topic: %s\n" +
                "Description: %s\n\n" +
                "Format the output as valid JSON:\n\n" +
                "{\n" +
                "    \"overview\": \"...\",\n" +
                "    \"keyConcepts\": [\"...\", \"...\"]\n" +
                "}\n";

        return String.format(template, topicName, description);
    }

    /**
     * Build a prompt for evaluating a user's answer to a question
     */
    public String evaluatePrompt(Question question, Answer answer) {
        return """
               Evaluate the following user's answer.

               Question: %s
               User Answer: %s

               The expected answer should cover these points:
               %s

               Return the feedback in JSON with the following fields:
               {
                   "score": 0-100,
                   "feedback": "..."
               }
               """.formatted(
                question.questionText(),
                answer.answer(),
                String.join(", ", question.expectedAnswerPoints())
        );
    }

    /**
     * Build a prompt for generating practice questions for a topic
     */
    public String practicePrompt(Topic topic, Difficulty difficulty, int numberOfQuestions) {
        return """
               Generate %d %s practice questions for the following topic:

               Topic: %s
               Description: %s

               Format the output as valid JSON:

               {
                   "questions": [
                       {
                           "questionText": "...",
                           "expectedAnswerPoints": ["...", "..."]
                       }
                   ]
               }

               Only return JSON, no extra text.
               """.formatted(
                numberOfQuestions,
                difficulty.name().toLowerCase(),
                topic.name(),
                topic.description() == null ? "" : topic.description()
        );
    }
}
