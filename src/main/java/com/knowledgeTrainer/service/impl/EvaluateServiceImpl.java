package com.knowledgeTrainer.service.impl;

import com.knowledgeTrainer.domain.*;
import com.knowledgeTrainer.service.AiContentService;
import com.knowledgeTrainer.service.EvaluateService;

import java.util.Optional;

public class EvaluateServiceImpl implements EvaluateService {
    private final AiContentService aiContentService;

    public EvaluateServiceImpl(AiContentService aiContentService) {
        this.aiContentService = aiContentService;
    }

    @Override
    public Optional<Feedback> evaluateAnswer(Topic topic, Question question, Answer answer) {
        return aiContentService.evaluateAnswer(topic, question, answer);
    }
}
