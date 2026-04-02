package Task_1;
import java.util.Random;
public class one {
    public static void main(String[] args) {
        Random rand = new Random();

        int number = rand.nextInt(100) + 1; // 1 to 100
        System.out.println("Random number: " + number);
    }
}
