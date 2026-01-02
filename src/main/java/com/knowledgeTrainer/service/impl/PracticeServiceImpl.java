package com.knowledgeTrainer.service.impl;

import com.knowledgeTrainer.domain.Difficulty;
import com.knowledgeTrainer.domain.PracticeSession;
import com.knowledgeTrainer.domain.Topic;
import com.knowledgeTrainer.service.AiContentService;
import com.knowledgeTrainer.service.PracticeService;

import java.util.Optional;

public class PracticeServiceImpl implements PracticeService {
    private final AiContentService aiContentService;

    public PracticeServiceImpl(AiContentService aiContentService) {
        this.aiContentService = aiContentService;
    }

    @Override
    public Optional<PracticeSession> createPracticeSession(Topic topic, Difficulty difficulty, int numberOfQuestions) {
        return aiContentService.generateQuestions(topic, difficulty, numberOfQuestions);
    }
}
