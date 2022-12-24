package banking;

import java.util.*;

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
        return  generateChecksum(BIN + String.format("%9d",customerAccountNumber).replace(" ","0"));
    }

    private static String generateChecksum(String cardNumber) {
        ArrayList<Integer> number = new ArrayList<>((Collection) Arrays.stream(cardNumber.split("")).map(Integer::parseInt));
        for (Integer num : number) {
            if (num % 2 != 0) {
                num *=2;
            }
        }

        for (Integer num : number) {
            if (num > 9) {
                num -= 9;
            }
        }
        int sum = number.stream().mapToInt(Integer::intValue).sum();
        number.add(10 - sum % 10);
        return number.toString();
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
            if (checkValidation(cardNumber, pin)) {
                System.out.println("You have successfully logged in!");
                startAccount();
                break;
            } else {
                System.out.println("Wrong card number or PIN!");
            }
        } while (checkValidation(cardNumber, pin));
    }

    private static void startAccount() {
        String input;
        do {
            String accountMenu = """
                1. Balance
                2. Log out
                0. Exit
                """;
            System.out.println(accountMenu);
            input = input();
            switch(input) {
                case "1":
                    System.out.println("Balance: 0");
                    System.out.println();
                    break;
                case "2":
                    System.out.println("You have successfully logged out!");
                    break;
                case "0" : exit();
                    break;
            }
        } while (!"2".equals(input));
    }

    private static boolean checkValidation(String cardNumber, String pin) {
        return Objects.equals(cardStorage.get(cardNumber), pin);
    }

    private static void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    private static String input() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}