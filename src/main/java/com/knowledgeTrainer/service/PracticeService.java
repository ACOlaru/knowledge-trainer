package com.knowledgeTrainer.service;

import com.knowledgeTrainer.domain.Difficulty;
import com.knowledgeTrainer.domain.PracticeSession;
import com.knowledgeTrainer.domain.Topic;

import java.util.Optional;

public interface PracticeService {
    Optional<PracticeSession> createPracticeSession(Topic topic, Difficulty difficulty, int numberOfQuestions);
}
