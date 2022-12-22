package banking;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {

    final private static String BIN = "400000";

    final private static Map<String, String> cardStorage = new HashMap<>();

    public static void main(String[] args) {
        startMenu();
    }

    private static void startMenu() {
        String input;
        do {
            String menu = """
                1. Create an account
                2. Log into account
                0. Exit
                """;
            System.out.println(menu);
            input = input();
            switch (input) {
                case "1":
                    createAnAccount();
                    break;
                case "2":
                    logIntoAccount();
                    break;
                case "0":
                    exit();
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        } while (!"0".equals(input));

    }

    private static void createAnAccount() {
        System.out.println("Your card has been created");
        generateCard();
    }

    private static void generateCard() {
        String cardNumber = generateCardNumber();
        String pin = generatePIN();
        cardStorage.put(cardNumber, pin);
        System.out.println("Your card number:");
        System.out.println(cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(pin);
    }

    private static String generateCardNumber() {
        Random random = new Random();
        int customerAccountNumber = random.nextInt(999999999);
        int checksum = random.nextInt(9);

        return BIN + String.format("%7d",customerAccountNumber).replace(" ","0") + checksum;
    }

    private static String generatePIN() {
        Random random = new Random();
        int pin = random.nextInt(9999);

        return String.format("%4d", pin).replace(" ", "0");
    }


    private static void logIntoAccount() {
        System.out.println("log in account");
    }

    private static void exit() {
        System.exit(0);
    }

    private static String input() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}