package com.knowledgeTrainer.service;

import com.knowledgeTrainer.domain.LearningContent;
import com.knowledgeTrainer.domain.Topic;

import java.util.Optional;

public interface LearningService {
    Optional<LearningContent> learn(Topic topic);
}
