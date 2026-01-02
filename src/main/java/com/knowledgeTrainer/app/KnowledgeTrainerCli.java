package com.knowledgeTrainer.app;

import com.knowledgeTrainer.domain.*;
import com.knowledgeTrainer.service.*;

import java.nio.file.Path;
import java.util.*;

public class KnowledgeTrainerCli {

    private final LearningService learnService;
    private final PracticeService practiceService;
    private final EvaluateService evaluationService;
    private final ExportService exportService;

    private final Scanner scanner = new Scanner(System.in);

    public KnowledgeTrainerCli(ApplicationContext context) {
        this.learnService = context.learnService();
        this.practiceService = context.practiceService();
        this.evaluationService = context.evaluationService();
        this.exportService = context.exportService();
    }

    public void start() {
        System.out.println("=== Knowledge Trainer ===");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> learn();
                    case "2" -> practice();
                    case "3" -> running = false;
                    default -> System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                // e.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1) Learn a topic");
        System.out.println("2) Practice a topic");
        System.out.println("3) Exit");
        System.out.print("> ");
    }

    private void learn() {
        boolean continueTopic = true;
        String name = null;
        while (continueTopic) {
            Topic topic = readTopic(name);

            Optional<LearningContent> content = learnService.learn(topic);

            if (content.isEmpty()) {
                System.out.println("Invalid topic");
            }

            System.out.println("\n--- Overview ---");
            System.out.println(content.get().overview());

            System.out.println("\n--- Key Concepts ---");
            content.get().keyConcepts().forEach(c -> System.out.println("- " + c));

            askExportLearning(content.orElse(null));
            continueTopic = askForContinue();
            name = topic.name();
        }

    }

    private boolean askForContinue() {
        System.out.print("Continue with this topic? (y/n): ");
        return scanner.nextLine().equalsIgnoreCase("y");
    }

    private void practice() {
        boolean continueTopic = true;
        String name = null;
        while (continueTopic) {
            Topic topic = readTopic(name);

            System.out.print("Difficulty (EASY/MEDIUM/HARD): ");
            Difficulty difficulty =
                    Difficulty.valueOf(scanner.nextLine().trim().toUpperCase());

            System.out.print("Number of questions: ");
            int n = Integer.parseInt(scanner.nextLine());

            Optional<PracticeSession> session =
                    practiceService.createPracticeSession(topic, difficulty, n);

            if (session.isEmpty()) {
                System.out.println("Invalid session");
            }

            for (Question q : session.get().questions()) {
                System.out.println("\nQuestion:");
                System.out.println(q.questionText());

                System.out.print("Your answer: ");
                String answerText = scanner.nextLine();

                Answer answer = new Answer(answerText);
                Optional<Feedback> feedback =
                        evaluationService.evaluateAnswer(topic, q, answer);
                session = Optional.of(session.get().withAnswer(q, answer, feedback.orElse(new Feedback(0, "No feedback"))));

                if (feedback.isEmpty()) {
                    System.out.println("Invalid feedback");
                }

                System.out.println("Score: " + feedback.get().score());
                System.out.println("Feedback: " + feedback.get().feedback());
            }

            askExportPractice(session.orElse(null));
            continueTopic = askForContinue();
            name = topic.name();
        }
    }

    private Topic readTopic(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.print("Topic name: ");
            name = scanner.nextLine();
        }

        System.out.print("Short description: ");
        String desc = scanner.nextLine();

        return new Topic(name, desc);
    }

    private void askExportLearning(LearningContent content) {
        System.out.print("Export to file? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            exportService.exportLearning(content, Path.of("learning.md"));
            System.out.println("Exported to learning.md");
        }
    }

    private void askExportPractice(PracticeSession session) {
        System.out.print("Export session? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            exportService.exportQuestions(session, Path.of("practice.md"));
            System.out.println("Exported to practice.md");
        }
    }
}




