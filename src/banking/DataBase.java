package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

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


}
