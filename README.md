# Knowledge Trainer 🧠

A lightweight, production-ready CLI application for learning and practicing any technical topic using AI-generated content.

Knowledge Trainer allows users to:

- Learn a topic through concise, structured summaries

- Practice with AI-generated questions

- Receive AI-based feedback on their answers

- Export learning sessions to Markdown for later review


# Features

📚 Learn mode – generates structured learning content for any topic

📝 Practice mode – generates questions with expected answer points

🤖 AI feedback – evaluates user answers and provides feedback

📄 Markdown export – saves sessions in a readable .md format

🛡 Error-resilient CLI – API or parsing errors never crash the app

# Technologies

- Java 21

- Maven

- OpenAI API (Chat Completions)

- Jackson (JSON parsing)

- Java HTTP Client

- CLI-based architecture

# Architecture Overview

````
├── app/                # User interaction & menu flow
├── domain/             # Topic, Question, Answer, Feedback, PracticeSession
├── infrastructure/     # OpenAI API integration
├── service/            # AI content generation & evaluation
├── util/               # JSON parsing, file export helpers
````


🚀 How to Run
1. Clone the repository
   git clone https://github.com/your-username/knowledge-trainer.git
   cd knowledge-trainer

2. Set your OpenAI API key and model
Recommended model: gpt-4o-mini
Set your key and model in application.properties file   

3. Build & run
   mvn clean package
   Run KnowledgeTrainerApplication