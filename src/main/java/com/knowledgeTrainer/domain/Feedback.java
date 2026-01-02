package com.knowledgeTrainer.domain;

public record Feedback(
        int score,
        String feedback
) {
    @Override
    public String toString() {
        return feedback + " with score " + score;
    }
}