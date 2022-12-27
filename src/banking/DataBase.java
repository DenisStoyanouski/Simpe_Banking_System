package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Objects;

import static banking.Main.input;

public class DataBase {
    static String url;
    static SQLiteDataSource dataSource;
    static void createDB (String fileName) {

        url = String.format("jdbc:sqlite:./%s", fileName);

        dataSource = new SQLiteDataSource();

        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                try (Statement statement = con.createStatement()) {
                    // Statement execution
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                            "id INTEGER PRIMARY KEY," +
                            "number TEXT NOT NULL," +
                            "pin TEXT NOT NULL," +
                            "balance INTEGER DEFAULT 0)");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Database doesn't respond");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static void addCard(int customerAccountNumber, String number, String pin) {
        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                try (Statement statement = con.createStatement()) {
                    // Statement execution
                    int i = statement.executeUpdate(String.format("INSERT INTO card " +
                            "VALUES (%d, '%s', '%s', 0)", customerAccountNumber, number, pin));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean logIn(String number, String pin) {
        boolean check = false;
        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                try (Statement statement = con.createStatement()) {
                    try (ResultSet card = statement.executeQuery(String.format("SELECT * FROM card where number = '%s'", number))) {
                        while (card.next()) {
                            // Retrieve column values
                            String pinDB = card.getString("pin");
                            if (Objects.equals(pin, pinDB)) {
                                check =  true;
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    static int getBalance(String number, String pin) {
        int balance = 0;
        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                try (Statement statement = con.createStatement()) {
                    try (ResultSet card = statement.executeQuery(String.format("SELECT * FROM card" +
                            " where number = '%s' AND pin = '%s'", number, pin))) {
                        while (card.next()) {
                            // Retrieve column values
                            balance = card.getInt("balance");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    static void addIncome() {
        int income = 0;
        System.out.println("Enter income:");
        try {
            income = Integer.parseInt(input());

            String insertIncome = "UPDATE card SET balance = ?";
            try (Connection con = dataSource.getConnection()) {
                if (con.isValid(5)) {
                    try (PreparedStatement statement = con.prepareStatement(insertIncome)) {
                        statement.setInt(1, income);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Income was added");
        } catch (NumberFormatException e) {
            System.out.println("You entered not number. Try again");
        }
    }

    static void closeAccount(String cardNumber) {
        String deleteAccount = "DELETE * FROM card WHERE number = '?'";

        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                try (PreparedStatement statement = con.prepareStatement(deleteAccount)) {
                    statement.setString(1, cardNumber);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("The account has been closed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
