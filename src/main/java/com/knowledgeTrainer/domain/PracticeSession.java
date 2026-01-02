package com.knowledgeTrainer.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record PracticeSession(
        Topic topic,
        List<Question> questions,
        Map<Question, Answer> answers,
        Map<Question, Feedback> feedbackMap
) {

    public PracticeSession withAnswer(Question q, Answer a, Feedback feedback) {
        Map<Question, Answer> newAnswers = new HashMap<>(answers);
        Map<Question, Feedback> newFeedbackMap = new HashMap<>(feedbackMap);
        newAnswers.put(q, a);
        newFeedbackMap.put(q, feedback);
        return new PracticeSession(topic, questions, newAnswers, newFeedbackMap);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(topic.toString()).append("\n");
        sb.append("With content");
        for (Question q : answers.keySet()) {
            sb.append(q.questionText()).append("\n");
            sb.append(answers.get(q).toString()).append("\n");
            sb.append(feedbackMap.get(q).toString()).append("\n");
        }

        return sb.toString();
    }
}