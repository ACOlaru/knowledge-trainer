package com.knowledgeTrainer.app;

public class KnowledgeTrainerApplication {
    public static void main(String[] args) {

        ApplicationContext context = new ApplicationContext();
        KnowledgeTrainerCli cli = new KnowledgeTrainerCli(context);

        cli.start();
    }
}
