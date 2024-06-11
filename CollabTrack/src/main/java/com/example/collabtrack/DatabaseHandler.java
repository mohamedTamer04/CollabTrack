package com.example.collabtrack;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHandler {

    private Connection connection;

    public DatabaseHandler() {
        try {
            // Establish the connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root", "LOLObadawi25@@");
        } catch (SQLException e) {
            showAlert("Database Connection Error", "Could not connect to the database. Please check your connection settings.");
            e.printStackTrace();
        }
    }

    public void insertMusician(String name, String email, String phoneNum, String genre, String language,
                               String gender, String bio, String username, String password, String instrument) {
        String sql = "INSERT INTO musicians (name, email, phone_num, genre, language, gender, bio, username, password, instrument) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phoneNum);
            pstmt.setString(4, genre);
            pstmt.setString(5, language);
            pstmt.setString(6, gender);
            pstmt.setString(7, bio);
            pstmt.setString(8, username);
            pstmt.setString(9, password);
            pstmt.setString(10, instrument);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showAlert("Database Error", "Could not insert musician. Please try again.");
            e.printStackTrace();
        }
    }

    public void insertSinger(String name, String email, String phoneNum, String genre, String language,
                             String gender, String bio, String username, String password) {
        String sql = "INSERT INTO singers (name, email, phone_num, genre, language, gender, bio, username, password) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phoneNum);
            pstmt.setString(4, genre);
            pstmt.setString(5, language);
            pstmt.setString(6, gender);
            pstmt.setString(7, bio);
            pstmt.setString(8, username);
            pstmt.setString(9, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showAlert("Database Error", "Could not insert singer. Please try again.");
            e.printStackTrace();
        }
    }

    public Musician loginMusician(String username, String password) {
        String sql = "SELECT * FROM musicians WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return new Musician(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_num"),
                        resultSet.getString("genre"),
                        resultSet.getString("language"),
                        resultSet.getString("gender"),
                        resultSet.getString("bio"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("instrument"),
                        resultSet.getInt("musician_id")
                );
            } else {
                showAlert("Login Failed", "Incorrect username or password. Please try again.");
                return null;
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred during login. Please try again.");
            e.printStackTrace();
            return null;
        }
    }

    public Singer loginSinger(String username, String password) {
        String sql = "SELECT * FROM singers WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                System.out.println(resultSet.getInt("singer_id"));
                return new Singer(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_num"),
                        resultSet.getString("genre"),
                        resultSet.getString("language"),
                        resultSet.getString("gender"),
                        resultSet.getString("bio"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("singer_id")
                );

            } else {
                showAlert("Login Failed", "Incorrect username or password. Please try again.");
                return null; // Login failed, singer not found
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred during login. Please try again.");
            e.printStackTrace();
            return null; // Error occurred during login process
        }
    }

    public boolean isAlreadyFollowing(int userId, int followedUserId) {
        String sql = "SELECT 1 FROM musician_followings WHERE user_id = ? AND followed_user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, followedUserId);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred while checking following status. Please try again.");
            e.printStackTrace();
            return false;
        }
    }

    public void saveFollowing(int userId, int followedUserId) {
        String sql = "INSERT INTO musician_followings (user_id, followed_user_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, followedUserId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showAlert("Database Error", "Could not save following. Please try again.");
            e.printStackTrace();
        }
    }

    public boolean isAlreadyFollowingSinger(int userId, int followedUserId) {
        String sql = "SELECT 1 FROM singer_followings WHERE user_id = ? AND followed_user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, followedUserId);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred while checking following status. Please try again.");
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Musician> getSingerFollowings(int singerId){
        ArrayList<Musician> musicians = new ArrayList<>();
        String sql = "SELECT m.* FROM musicians m JOIN singer_followings mf ON musician_id = mf.followed_user_id WHERE user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Check if singerId is valid
            if (singerId < 0) {
                System.err.println("Invalid singerId: " + singerId);
                return musicians;
            }

            pstmt.setInt(1, singerId);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    // Construct Musician object and add to the list
                    Musician musician = new Musician(
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone_num"),
                            resultSet.getString("genre"),
                            resultSet.getString("language"),
                            resultSet.getString("gender"),
                            resultSet.getString("bio"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("instrument"),
                            resultSet.getInt("musician_id")
                    );
                    musicians.add(musician);
                    musician.getInstrument();
                }
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred while retrieving followings. Please try again.");
            e.printStackTrace();
        }

        return musicians;
    }

    public void saveFollowingSinger(int userId, int followedUserId) {
        String sql = "INSERT INTO singer_followings (user_id, followed_user_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, followedUserId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showAlert("Database Error", "Could not save following. Please try again.");
            e.printStackTrace();
        }
    }

    public ArrayList<Singer> getAllSingers() {
        ArrayList<Singer> singers = new ArrayList<>();
        String sql = "SELECT * FROM singers"; // Assuming you have a singers table

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                Singer singer = new Singer(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_num"),
                        resultSet.getString("genre"),
                                resultSet.getString("language"),
                                resultSet.getString("gender"),
                                resultSet.getString("bio"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getInt("singer_id")
                        );
                singers.add(singer);
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred while retrieving singers. Please try again.");
            e.printStackTrace();
        }

        return singers;
    }

    public ArrayList<Musician> getAllMusicians() {
        ArrayList<Musician> musicians = new ArrayList<>();
        String sql = "SELECT * FROM musicians"; // Assuming you have a musicians table

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                Musician musician = new Musician(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_num"),
                        resultSet.getString("genre"),
                        resultSet.getString("language"),
                        resultSet.getString("gender"),
                        resultSet.getString("bio"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("instrument"),
                        resultSet.getInt("musician_id")
                );
                musicians.add(musician);
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred while retrieving musicians. Please try again.");
            e.printStackTrace();
        }

        return musicians;
    }

    public boolean insertMessage(int musicianId, String message, int singerId) {
        String query = "INSERT INTO musician_messages (musician_id, message, singer_id) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, musicianId);
            preparedStatement.setString(2, message);
            preparedStatement.setInt(3, singerId);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred while inserting the message. Please try again.");
            e.printStackTrace();
            return false;
        }
    }

    public int getSingerIdByUsername(String username) {
        String sql = "SELECT singer_id FROM singers WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("singer_id");
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred while retrieving singer ID. Please try again.");
            e.printStackTrace();
        }
        return -1; // User not found
    }

    public ArrayList<String> getMusicianMessages(int musicianId) {
        ArrayList<String> messages = new ArrayList<>();
        String sql = "SELECT * FROM musician_messages WHERE musician_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, musicianId);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                messages.add(resultSet.getString("message") + "|" + resultSet.getInt("singer_id"));
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred while retrieving messages. Please try again.");
            e.printStackTrace();
        }
        return messages;
    }

    public String getSingerUsername(int singerId) {
        String sql = "SELECT username FROM singers WHERE singer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, singerId);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("username");
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred while retrieving singer username. Please try again.");
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred while closing the database connection.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

