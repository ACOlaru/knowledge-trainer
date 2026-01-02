package com.knowledgeTrainer.service;

import com.knowledgeTrainer.domain.LearningContent;
import com.knowledgeTrainer.domain.PracticeSession;

import java.nio.file.Path;

public interface ExportService {
    void exportLearning(LearningContent learningContent, Path path);
    void exportQuestions(PracticeSession practiceSession, Path path);
}
