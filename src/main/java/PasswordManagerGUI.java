import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.geometry.Insets;
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

        Text masterTitle = new Text("Welcome to Password Manager");
        masterTitle.setFill(Color.WHITE);
        masterTitle.setEffect(masterOutline);
        masterTitle.setFont(Font.font("Segoe UI", 90));
        VBox.setMargin(masterTitle, new Insets(0, 0, 100, 0));

        Text enterPassText = new Text("Enter your password:");
        styleText(enterPassText);
        TextField masterPassword = new TextField();
        styleTextField(masterPassword);
        masterPassword.setPromptText("Enter up to 30 characters");
        TextFormatter<String> masterPasswordFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change;
            } else {
                return null; // Reject the change
            }
        });
        masterPassword.setTextFormatter(masterPasswordFormatter);
        Text warning = new Text();
        styleWarning(warning);
        // --- First time loading the program ---
        Text chooseQuestionText = new Text("Choose your recovery question:");
        styleText(chooseQuestionText);
        ComboBox<String> recoveryQuestion = new ComboBox<>();
        recoveryQuestion.getItems().addAll("What is your mother's mother's name?", "What was the name of your first pet?", "In what city were you born?");
        // First question by defult
        recoveryQuestion.setValue("What is your mother's mother's name?");
        styleComboBox(recoveryQuestion);
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
        styleTextField(recoveryAnswer);
        Button signUpButton = new Button("Sign Up");
        styleButton(signUpButton);
        // --- Regular loading of the program ---
        AtomicInteger loginAttemptsLeft = new AtomicInteger(3);
        Button LoginButton = new Button("Login");
        Text loginAttemptText = new Text("Attempts Left: " + loginAttemptsLeft.toString());
        styleButton(LoginButton);
        styleText(loginAttemptText);
        // Signup or Login Check
        if (MasterPasswordManager.isFirstTime()) {
            masterLayout.getChildren().addAll(masterTitle, enterPassText, masterPassword, chooseQuestionText, recoveryQuestion, recoveryAnswer, signUpButton, warning);
        } else {
            masterLayout.getChildren().addAll(masterTitle, enterPassText, masterPassword, loginAttemptText, LoginButton, warning);
        }
        Scene masterScene = new Scene(masterLayout, 1920, 1080);

        // === Master Password Recovery Page ===

        VBox masterRecoveryLayout = new VBox(20);
        masterRecoveryLayout.setAlignment(Pos.CENTER);
        masterRecoveryLayout.setStyle("-fx-background-color: linear-gradient(to bottom,rgb(108, 184, 255),rgb(45, 115, 164));");
        // Set Outline Effect
        DropShadow masterRecoveryOutline = new DropShadow();
        masterRecoveryOutline.setOffsetX(0);
        masterRecoveryOutline.setOffsetY(0);
        masterRecoveryOutline.setColor(Color.BLACK);      // Outline color
        masterRecoveryOutline.setRadius(15);               // Thickness

        Text masterRecoveryTitle = new Text("Recover Master Password");
        masterRecoveryTitle.setFill(Color.WHITE);
        masterRecoveryTitle.setEffect(masterOutline);
        masterRecoveryTitle.setFont(Font.font("Segoe UI", 90));
        VBox.setMargin(masterRecoveryTitle, new Insets(0, 0, 100, 0));

        Text masterRecoveryQuestion = new Text(MasterPasswordManager.getQuestion());
        styleText(masterRecoveryQuestion);
        TextField masterRecoveryAnswer = new TextField();
        masterRecoveryAnswer.setPromptText("Enter up to 20 characters");
        TextFormatter<String> recoveryAnswerFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            } else {
                return null; // Reject the change
            }
        });
        masterRecoveryAnswer.setTextFormatter(recoveryAnswerFormatter);
        styleTextField(masterRecoveryAnswer);

        Text textPasswordRecovery = new Text("Enter new password");
        styleText(textPasswordRecovery);
        TextField masterPasswordRecovery = new TextField();
        masterPasswordRecovery.setPromptText("Enter up to 30 characters");
        TextFormatter<String> recoveryPasswordFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            } else {
                return null; // Reject the change
            }
        });
        masterPasswordRecovery.setTextFormatter(recoveryPasswordFormatter);
        styleTextField(masterPasswordRecovery);

        Button recoveryButton = new Button("Recover Password");
        styleButton(recoveryButton);

        Text recoveryWarning = new Text();
        styleWarning(recoveryWarning);

        masterRecoveryLayout.getChildren().addAll(masterRecoveryTitle, masterRecoveryQuestion, masterRecoveryAnswer, textPasswordRecovery, masterPasswordRecovery, recoveryButton, recoveryWarning);

        Scene masterRecoveryScene = new Scene(masterRecoveryLayout, 1920, 1080);

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
        
        signUpButton.setOnAction(e -> {
            String masterPassString = masterPassword.getText();
            String recoveryAnswerString = recoveryAnswer.getText();
            String recoveryQuestionString = recoveryQuestion.getValue();
            if (masterPassString.length() < 6) {
                warning.setVisible(true);
                warning.setText("Password needs to be at least 6 characters");
            } else if (recoveryAnswerString.length() == 0) {
                warning.setVisible(true);
                warning.setText("Answer is missing");
            } else {
                MasterPasswordManager.createMasterPassword(masterPassString, recoveryQuestionString, recoveryAnswerString);
                stage.setScene(mainMenu);
            }
        });

        LoginButton.setOnAction(e -> {
            String masterPassString = masterPassword.getText();
            if (MasterPasswordManager.checkPassword(masterPassString)) {
                stage.setScene(mainMenu);
            } else {
                loginAttemptsLeft.decrementAndGet();
                loginAttemptText.setText("Attempts Left: " + loginAttemptsLeft.toString());
                warning.setVisible(true);
                warning.setText("Password is not correct");
                if (loginAttemptsLeft.get() == 0) {
                    stage.setScene(masterRecoveryScene);
                }
            }
        });

        recoveryButton.setOnAction(e -> {
            String answer = masterRecoveryAnswer.getText();
            String newPass = masterPasswordRecovery.getText();

            if (newPass.length() < 6) {
                recoveryWarning.setVisible(true);
                recoveryWarning.setText("Password needs to be at least 6 characters");
            } else if (MasterPasswordManager.recoverPassword(answer, newPass)) {
                stage.setScene(mainMenu);
            } else {
                recoveryWarning.setVisible(true);
                recoveryWarning.setText("Answer is not correct");
            }
        });

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
        btn.setFont(Font.font("Verdana", 30));
        String baseStyle = "-fx-background-color:rgb(5, 71, 116); -fx-text-fill: white; -fx-padding: 15 30 15 30;";
        String hoverStyle = "-fx-background-color: rgb(12, 116, 214); -fx-text-fill: white; -fx-padding: 15 30 15 30;";
    
        btn.setStyle(baseStyle);

        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(baseStyle));
    }

    // Reusable text styling
    private void styleText(Text txt) {
        txt.setStyle("-fx-font-size: 18px;" +
                     "-fx-font-weight: bold;" +
                     "-fx-fill: #333;");
    }   

    // Reusable warning styling
    private void styleWarning(Text txt) {
        txt.setStyle("-fx-font-size: 18px;" +
                     "-fx-font-weight: bold;" +
                     "-fx-fill: rgb(226, 11, 11);");
        txt.setVisible(false);
    }   

    // Reusable text field styling
    private void styleTextField(TextField tf) {
        tf.setStyle("-fx-font-size: 14px;" +
                "-fx-background-color: #f0f0f0;" +
                "-fx-border-color: #ccc;" +
                "-fx-border-radius: 4;" +
                "-fx-background-radius: 4;" +
                "-fx-padding: 6 10;");
        tf.setMaxWidth(300);
    
    }

    // Reusable combo box styling
    private void styleComboBox(ComboBox<?> comboBox) {
        comboBox.setStyle("-fx-font-size: 14px;" +
                      "-fx-padding: 5 10 5 10;" +
                      "-fx-background-color: #f0f0f0;" +
                      "-fx-border-color: #ccc;" +
                      "-fx-border-radius: 5;" +
                      "-fx-background-radius: 5;");
    }


    public static void main(String[] args) {
        launch();
    }
}
