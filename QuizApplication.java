import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

// Class to represent a Quiz Question
class QuizQuestion {
    private String question;
    private String[] options;
    private int correctAnswerIndex;

    public QuizQuestion(String question, String[] options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

// Class to represent the Quiz Application
class Quiz {
    private QuizQuestion[] questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;
    private boolean timeUp;

    public Quiz(QuizQuestion[] questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.timer = new Timer();
    }

    // Start the quiz
    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);

        for (currentQuestionIndex = 0; currentQuestionIndex < questions.length; currentQuestionIndex++) {
            displayQuestion();

            // Start the timer for the question
            startTimer();

            int userAnswer = -1;

            while (!timeUp && userAnswer == -1) {
                System.out.print("Enter your answer (1-4): ");
                userAnswer = scanner.nextInt();

                if (userAnswer < 1 || userAnswer > 4) {
                    System.out.println("Invalid choice. Please select a valid option.");
                    userAnswer = -1;
                }
            }

            // Cancel the timer if the user answers within time
            timer.cancel();
            timer = new Timer(); // Reset timer for next question

            if (timeUp) {
                System.out.println("Time's up! Moving to the next question.");
            } else if (userAnswer == questions[currentQuestionIndex].getCorrectAnswerIndex() + 1) {
                score++;
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect.");
            }
            System.out.println();
        }

        displayResult();
        scanner.close();
    }

    // Display the current question and options
    private void displayQuestion() {
        QuizQuestion currentQuestion = questions[currentQuestionIndex];
        System.out.println("Question " + (currentQuestionIndex + 1) + ": " + currentQuestion.getQuestion());

        String[] options = currentQuestion.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    // Start a timer of 10 seconds for each question
    private void startTimer() {
        timeUp = false;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeUp = true;
            }
        }, 10000); // 10 seconds
    }

    // Display the final result
    private void displayResult() {
        System.out.println("Quiz Over!");
        System.out.println("Your final score is: " + score + "/" + questions.length);
        System.out.println("Summary:");

        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + questions[i].getQuestion());
            System.out.println("Correct Answer: " + questions[i].getOptions()[questions[i].getCorrectAnswerIndex()]);
        }
    }
}

// Main class to run the Quiz Application
public class QuizApplication {
    public static void main(String[] args) {
        // Sample quiz questions
        QuizQuestion[] questions = {
            new QuizQuestion("What is the capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Rome"}, 2),
            new QuizQuestion("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 1),
            new QuizQuestion("Who wrote 'Hamlet'?", new String[]{"Charles Dickens", "Jane Austen", "Mark Twain", "William Shakespeare"}, 3),
            new QuizQuestion("What is the largest ocean on Earth?", new String[]{"Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"}, 3)
        };

        // Start the quiz
        Quiz quiz = new Quiz(questions);
        quiz.startQuiz();
    }
}
