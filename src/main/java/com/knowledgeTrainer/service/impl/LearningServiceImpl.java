package com.knowledgeTrainer.service.impl;

import com.knowledgeTrainer.domain.LearningContent;
import com.knowledgeTrainer.domain.Topic;
import com.knowledgeTrainer.service.AiContentService;
import com.knowledgeTrainer.service.LearningService;

import java.util.Optional;

public class LearningServiceImpl implements LearningService {
    private final AiContentService ai;

    public LearningServiceImpl(AiContentService ai) {
        this.ai = ai;
    }

    @Override
    public Optional<LearningContent> learn(Topic topic) {
        return ai.generateLearningContent(topic);
    }
}
