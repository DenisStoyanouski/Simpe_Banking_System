package banking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        startMenu();
    }

    private static void startMenu() {
        String menu = """
                1. Create an account
                2. Log into account
                0. Exit
                """;
        System.out.println(menu);
        String input = input();
        do {
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
        }
            while (!"0".equals(input)) ;
    }

    private static void createAnAccount() {
        System.out.println("create account");
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