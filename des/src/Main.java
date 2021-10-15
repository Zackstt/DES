//package Des;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        int option = 0;
        while (!(option == 1 || option == 2)) {
            String optionPrompt = "Select between the options: \n 1. Encrypt \n 2. Decrypt \n";
            System.out.println(optionPrompt);
            option = userInput.nextInt();
        }

        Des encryptionMode = new Des(option);
        String inputText = "";
        String outputText = "";
    }
}