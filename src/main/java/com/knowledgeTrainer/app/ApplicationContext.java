package com.knowledgeTrainer.app;

import com.knowledgeTrainer.infrastructure.*;
import com.knowledgeTrainer.service.*;
import com.knowledgeTrainer.service.impl.*;
import com.knowledgeTrainer.utils.FileSystemWriter;
import com.knowledgeTrainer.utils.PromptBuilder;

public class ApplicationContext {

    private final LearningService learnService;
    private final PracticeService practiceService;
    private final EvaluateService evaluationService;
    private final ExportService exportService;

    public ApplicationContext() {
        OpenAiClient client = new OpenAiClient();
        PromptBuilder promptBuilder = new PromptBuilder();
        AiContentService aiService = new OpenAiContentService(client, promptBuilder);

        this.learnService = new LearningServiceImpl(aiService);
        this.practiceService = new PracticeServiceImpl(aiService);
        this.evaluationService = new EvaluateServiceImpl(aiService);
        this.exportService = new ExportServiceImpl(new FileSystemWriter());
    }

    public LearningService learnService() { return learnService; }
    public PracticeService practiceService() { return practiceService; }
    public EvaluateService evaluationService() { return evaluationService; }
    public ExportService exportService() { return exportService; }
}
