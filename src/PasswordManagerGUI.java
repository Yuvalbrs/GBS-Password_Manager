import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

public class PasswordManagerGUI extends Application {

    @Override
    public void start(Stage stage) {
        // === Master Password Check ===
        VBox masterLayout = new VBox(20);
        masterLayout.setAlignment(Pos.CENTER);
        masterLayout.setStyle("-fx-background-color: linear-gradient(to bottom,rgb(108, 184, 255),rgb(45, 115, 164));");
        // Set Outline Effect
        DropShadow masterOutline = new DropShadow();
        masterOutline.setOffsetX(0);
        masterOutline.setOffsetY(0);
        masterOutline.setColor(Color.BLACK);      // Outline color
        masterOutline.setRadius(15);               // Thickness

        Text masterTitle = new Text("Login To Password Manager");
        masterTitle.setFill(Color.WHITE);
        masterTitle.setEffect(masterOutline);
        masterTitle.setFont(Font.font("Segoe UI", 90));

        Text enterPassText = new Text("Enter your password:");
        TextField masterPassword = new TextField();
        masterPassword.setPromptText("Enter up to 30 characters");
        TextFormatter<String> masterPasswordFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change;
            } else {
                return null; // Reject the change
            }
        });
        masterPassword.setTextFormatter(masterPasswordFormatter);
        if (MasterPasswordManager.isFirstTime()) {
            Text chooseQuestionText = new Text("Choose your recovery question:");
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll("What is your mother's mother's name?", "What was the name of your first pet?", "In what city were you born?");
            // First question by defult
            comboBox.setValue("What is your mother's mother's name?");
            comboBox.setOnAction(e -> {
                String recoveryQuestion = comboBox.getValue();
            });
            TextField recoveryAnswer = new TextField();
            recoveryAnswer.setPromptText("Enter up to 20 characters");
            TextFormatter<String> answerFormatter = new TextFormatter<>(change -> {
                if (change.getControlNewText().length() <= 20) {
                    return change;
                } else {
                    return null; // Reject the change
                }
            });
            recoveryAnswer.setTextFormatter(answerFormatter);
        }
        masterLayout.getChildren().addAll(masterTitle, enterPassText);
        masterLayout.setAlignment(Pos.CENTER);
        Scene masterScene = new Scene(masterLayout, 1920, 1080);

        // === Main Menu ===
        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: linear-gradient(to bottom,rgb(108, 184, 255),rgb(45, 115, 164));");

        // Set Outline Effect
        DropShadow outline = new DropShadow();
        outline.setOffsetX(0);
        outline.setOffsetY(0);
        outline.setColor(Color.BLACK);      // Outline color
        outline.setRadius(15);               // Thickness

        Text title = new Text("Password Manager");
        title.setFill(Color.WHITE);
        title.setEffect(outline);
        title.setFont(Font.font("Segoe UI", 90));

        Button addPasswordBtn = new Button("Add Password");
        Button loadPasswordsBtn = new Button("Load Passwords");
        Button deletePasswordBtn = new Button("Delete Password");

        styleButton(addPasswordBtn);
        styleButton(loadPasswordsBtn);
        styleButton(deletePasswordBtn);

        menuLayout.getChildren().addAll(title, addPasswordBtn, loadPasswordsBtn, deletePasswordBtn);
        Scene mainMenu = new Scene(menuLayout, 1920, 1080);

        // === Add Password Screen ===
        VBox addLayout = new VBox(20);
        addLayout.setAlignment(Pos.CENTER);
        addLayout.setStyle("-fx-background-color: linear-gradient(to bottom,rgb(108, 184, 255),rgb(45, 115, 164));");
        Text addText = new Text("Add Password Screen");
        Button backFromAdd = new Button("Back");
        styleButton(backFromAdd);
        addLayout.getChildren().addAll(addText, backFromAdd);
        Scene addScene = new Scene(addLayout, 1920, 1080);

        // === Load Passwords Screen ===
        VBox loadLayout = new VBox(20);
        loadLayout.setAlignment(Pos.CENTER);
        loadLayout.setStyle("-fx-background-color: linear-gradient(to bottom,rgb(108, 184, 255),rgb(45, 115, 164));");
        Text loadText = new Text("Load Passwords Screen");
        Button backFromLoad = new Button("Back");
        styleButton(backFromLoad);
        loadLayout.getChildren().addAll(loadText, backFromLoad);
        Scene loadScene = new Scene(loadLayout, 1920, 1080);

        // === Delete Password Screen ===
        VBox deleteLayout = new VBox(20);
        deleteLayout.setAlignment(Pos.CENTER);
        deleteLayout.setStyle("-fx-background-color: linear-gradient(to bottom,rgb(108, 184, 255),rgb(45, 115, 164));");
        Text deleteText = new Text("Delete Password Screen");
        Button backFromDelete = new Button("Back");
        styleButton(backFromDelete);
        deleteLayout.getChildren().addAll(deleteText, backFromDelete);
        Scene deleteScene = new Scene(deleteLayout, 1920, 1080);

        // === Scene Switching Logic ===
        addPasswordBtn.setOnAction(e -> stage.setScene(addScene));
        loadPasswordsBtn.setOnAction(e -> stage.setScene(loadScene));
        deletePasswordBtn.setOnAction(e -> stage.setScene(deleteScene));

        backFromAdd.setOnAction(e -> stage.setScene(mainMenu));
        backFromLoad.setOnAction(e -> stage.setScene(mainMenu));
        backFromDelete.setOnAction(e -> stage.setScene(mainMenu));

        // === Launch Master Password Check ===
        stage.setTitle("Password Manager GUI");
        stage.setScene(masterScene);
        stage.show();
    }

    // Reusable button styling
    private void styleButton(Button btn) {
        btn.setFont(Font.font("Verdana", 60));
        String baseStyle = "-fx-background-color:rgb(5, 71, 116); -fx-text-fill: white; -fx-padding: 15 30 15 30;";
        String hoverStyle = "-fx-background-color: rgb(12, 116, 214); -fx-text-fill: white; -fx-padding: 15 30 15 30;";
    
        btn.setStyle(baseStyle);

        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(baseStyle));
    }


    public static void main(String[] args) {
        launch();
    }
}
