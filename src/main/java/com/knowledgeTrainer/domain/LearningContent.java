package com.knowledgeTrainer.domain;

import java.util.List;

public record LearningContent(
        String overview,
        List<String> keyConcepts,
        List<String> examples,
        List<String> commonMistakes,
        List<String> furtherStudySuggestions
) {}
