import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class PlayerManagerApp extends Application {
    private DatabaseManager dbManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Player Manager");

        TextField playerNameField = new TextField();
        TextField highScoreField = new TextField();
        TextField levelField = new TextField();
        ComboBox<String> nationalComboBox = new ComboBox<>();

        Button addButton = new Button("Add Player");
        Button deleteButton = new Button("Delete Player");
        Button showAllButton = new Button("Show All Players");
        Button findButton = new Button("Find Player");
        Button showTop10Button = new Button("Show Top 10 Players");

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(new Label("Player Name:"), playerNameField,
                new Label("High Score:"), highScoreField,
                new Label("Level:"), levelField,
                new Label("National:"), nationalComboBox,
                addButton, deleteButton, showAllButton,
                findButton, showTop10Button, outputArea);

        Scene scene = new Scene(layout, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        addButton.setOnAction(e -> {
            try {
                String name = playerNameField.getText();
                int highScore = Integer.parseInt(highScoreField.getText());
                int level = Integer.parseInt(levelField.getText());
                int nationalId = 1; // Replace with actual selected national ID

                dbManager.insertPlayer(new Player(nationalId, name, highScore, level));
                outputArea.appendText("Player added: " + name + "\n");
            } catch (SQLException | NumberFormatException ex) {
                outputArea.appendText("Error: " + ex.getMessage() + "\n");
            }
        });

        deleteButton.setOnAction(e -> {
        });

        showAllButton.setOnAction(e -> {
            try {
                List<Player> players = dbManager.displayAll();
                outputArea.clear();
                for (Player p : players) {
                    outputArea.appendText(p.getPlayerName() + "\n");
                }
            } catch (SQLException ex) {
                outputArea.appendText("Error: " + ex.getMessage() + "\n");
            }
        });

        findButton.setOnAction(e -> {
        });

        showTop10Button.setOnAction(e -> {
            try {
                List<Player> players = dbManager.displayTop10();
                outputArea.clear();
                for (Player p : players) {
                    outputArea.appendText(p.getPlayerName() + ": " + p.getHighScore() + "\n");
                }
            } catch (SQLException ex) {
                outputArea.appendText("Error: " + ex.getMessage() + "\n");
            }
        });
    }

    @Override
    public void init() {
        try {
            dbManager = new DatabaseManager();
        } catch (SQLException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
        }
    }

    @Override
    public void stop() {
        try {
            if (dbManager != null) {
                dbManager.closeConnection();
            }
        } catch (SQLException ex) {
            System.err.println("Error closing connection: " + ex.getMessage());
        }
    }
}
