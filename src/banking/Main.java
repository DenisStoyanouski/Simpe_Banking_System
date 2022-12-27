package banking;

import org.sqlite.SQLiteDataSource;

import java.util.*;
import java.util.stream.Collectors;

import static banking.DataBase.*;

public class Main {

    final private static String BIN = "400000";
    private static int customerAccountNumber;

    public static void main(String[] args) {
        if (args.length != 0) {
            String fileName = args[1];
            createDB(fileName);
        }
        startMenu();
    }

    private static void startMenu() {
        String input;
        do {
            String menu = "1. Create an account\n" +
                            "2. Log into account\n" +
                            "0. Exit\n";
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
        System.out.println();
        System.out.println("Your card has been created");
        generateCard();
    }

    private static void generateCard() {
        String cardNumber = generateCardNumber();
        String pin = generatePIN();
        addCard(customerAccountNumber, cardNumber, pin);
        System.out.println("Your card number:");
        System.out.println(cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(pin);
        System.out.println();
    }

    private static String generateCardNumber() {
        Random random = new Random();
        customerAccountNumber = random.nextInt(999999999);
        String cardNumber = BIN + String.format("%9d",customerAccountNumber).replace(" ","0");
        return  generateChecksum(cardNumber);
    }

    private static String generateChecksum(String cardNumber) {
        ArrayList<Integer> number = new ArrayList<>(Arrays.stream(cardNumber.split("")).map(Integer::parseInt).collect(Collectors.toList()));
        // The Luhn algorithm
        // Multiply odd indexes by 2
        for (int i = 0; i < number.size(); i += 2) {
            number.set(i, number.get(i) * 2);
        }
       // Subtract 9 to numbers over 9
        for (int i = 0; i < number.size(); i++) {
            if (number.get(i) > 9) {
                number.set(i, number.get(i) - 9);
            }
        }
        // Add all numbers
        int sum = number.stream().mapToInt(Integer::intValue).sum();
        // If the received number is divisible by 10 with the remainder equal to zero, then this number is valid;
        // otherwise, the card number is not valid.
        // Add 16th number which satisfies this condition
        int checksum = 10 - sum % 10;
        if (checksum == 10) {
            checksum = 0;
        }
        return cardNumber.concat(String.valueOf(checksum));
    }

    private static String generatePIN() {
        Random random = new Random();
        int pin = random.nextInt(9999);

        return String.format("%4d", pin).replace(" ", "0");
    }


    private static void logIntoAccount() {
        String cardNumber;
        String pin;
        do {
            System.out.println("Enter your card number:");
            cardNumber = input();
            System.out.println("Enter your PIN:");
            pin = input();
            if (logIn(cardNumber, pin)) {
                System.out.println("You have successfully logged in!");
                startAccount(cardNumber, pin);
                break;
            } else {
                System.out.println("Wrong card number or PIN!");
            }
        } while (logIn(cardNumber, pin));
    }

    private static void startAccount(String cardNumber, String pin) {
        String input;
        do {
            String accountMenu =
                    "1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                            "0. Exit\n";
            System.out.println(accountMenu);
            input = input();
            switch(input) {
                case "1":
                    System.out.printf("Balance: %d", getBalance(cardNumber, pin));
                    System.out.println();
                    break;
                case "2" : addIncome(cardNumber);
                break;
                case "5":
                    System.out.println("You have successfully logged out!");
                    break;
                case "0" : exit();
                    break;
            }
        } while (!"0".equals(input));
    }

    private static void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    static String input() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}