package com.knowledgeTrainer.service.impl;

import com.knowledgeTrainer.domain.LearningContent;
import com.knowledgeTrainer.domain.PracticeSession;
import com.knowledgeTrainer.service.ExportService;
import com.knowledgeTrainer.utils.FileSystemWriter;

import java.nio.file.Path;

public class ExportServiceImpl implements ExportService {

    private final FileSystemWriter writer;

    public ExportServiceImpl(FileSystemWriter writer) {
        this.writer = writer;
    }

    @Override
    public void exportLearning(LearningContent content, Path path) {
        writer.write(path, toMarkdownContent(content));
    }

    @Override
    public void exportQuestions(PracticeSession session, Path path) {
        writer.write(path, toMarkdownSession(session));
    }

    private String toMarkdownContent(LearningContent content) {
        return """
            # Learning Content

            ## Overview
            %s
            """.formatted(content.overview());
    }

    private String toMarkdownSession(PracticeSession practiceSession) {
        return """
            # Practice session

            ## Overview
            %s
            """.formatted(practiceSession.toString());
    }
}
