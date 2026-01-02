package com.knowledgeTrainer.domain;

import java.util.List;

public record Question(
        String questionText,
        List<String> expectedAnswerPoints
) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(questionText);
        sb.append("\n");
        sb.append("ExpectedAnswerPoints: ");
        for (String expectedAnswerPoint : expectedAnswerPoints) {
            sb.append(expectedAnswerPoint);
        }

        return sb.toString();
    }
}
