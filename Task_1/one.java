package Task_1;
import java.util.*;
public class one {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        int totalScore = 0;
        int roundsWon = 0;

        System.out.println("=== Guess the Number Game ===");

        boolean playAgain = true;

        while (playAgain) {
            int number = rand.nextInt(100) + 1; // 1 to 100
            int attempts = 0;
            int maxAttempts = 5;
            boolean guessedCorrectly = false;

            System.out.println("\nNew Round Started!");
            System.out.println("Guess a number between 1 and 100");
            System.out.println("You have " + maxAttempts + " attempts.");

            while (attempts < maxAttempts) {
                System.out.println("the number is between "+(number - 5)+" to "+(number + 5));//for the guess to be easy
                System.out.print("Enter your guess: ");
                int guess = sc.nextInt();
                attempts++;

                if (guess == number) {
                    System.out.println("🎉 Correct! You guessed the number.");
                    guessedCorrectly = true;

                    // Score based on attempts (fewer attempts = higher score)
                    int score = (maxAttempts - attempts + 1) * 10;
                    totalScore += score;
                    roundsWon++;

                    System.out.println("Score this round: " + score);
                    break;

                } else if (guess > number) {
                    System.out.println("Too high!");
                } else {
                    System.out.println("Too low!");
                }

                System.out.println("Attempts left: " + (maxAttempts - attempts));
            }

            if (!guessedCorrectly) {
                System.out.println(" Out of attempts! The correct number was: " + number);
            }

            // Ask to play again
            System.out.print("\nDo you want to play again? (yes/no): ");
            String choice = sc.next();

            if (!choice.equalsIgnoreCase("yes")) {
                playAgain = false;
            }
        }

        
        System.out.println("\n=== Game Over ===");// Final results
        System.out.println("Rounds won: " + roundsWon);
        System.out.println("Total score: " + totalScore);

        sc.close();
    }
}