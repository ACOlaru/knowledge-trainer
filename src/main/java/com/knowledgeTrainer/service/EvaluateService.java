package com.knowledgeTrainer.service;

import com.knowledgeTrainer.domain.*;

import java.util.Optional;

public interface EvaluateService {
    Optional<Feedback> evaluateAnswer(Topic topic, Question question, Answer answer);
}
