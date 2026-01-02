package com.knowledgeTrainer.service;

import com.knowledgeTrainer.domain.*;

import java.util.Optional;

public interface AiContentService {
    Optional<LearningContent> generateLearningContent(Topic topic);
    Optional<PracticeSession> generateQuestions(Topic topic, Difficulty difficulty, int numberOfQuestions);
    Optional<Feedback> evaluateAnswer(Topic topic, Question question, Answer answer);
}
