package UI;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class Controller {
    private final String SOURCE_FILE_CHOOSER_TITLE = "Select Source File";
    private final String TARGET_DIRECTORY_CHOOSER_TITLE = "Select Target Directory";

    @FXML
    private Button encryptButton;
    @FXML
    private Button decryptButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label sourceLabel;
    @FXML
    private TextField sourceField;
    @FXML
    private Label targetLabel;
    @FXML
    private TextField targetField;
    @FXML
    private Label iterationLabel;
    @FXML
    private ChoiceBox<String> iterationChoice;
    @FXML
    private CheckBox deleteFileCheck;
    @FXML
    private Button sourceButton;
    @FXML
    private Button targetButton;

    public Controller() {}

    @FXML
    private void sourceLocationButtonClicked(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(SOURCE_FILE_CHOOSER_TITLE);

        File file = fileChooser.showOpenDialog(null);
        if (file != null){
            sourceField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void targetLocationButtonClicked(MouseEvent mouseEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(TARGET_DIRECTORY_CHOOSER_TITLE);

        File file = directoryChooser.showDialog(null);
        if (file != null){
            targetField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void encryptButtonClicked(MouseEvent mouseEvent) {
        if (checkUIFormConstraints()){

        }
    }

    @FXML
    private void decryptButtonClicked(MouseEvent mouseEvent) {
        if (checkUIFormConstraints()){

        }
    }

    private boolean checkUIFormConstraints(){
        return (sourceField.getText() != null && !sourceField.getText().isEmpty()) &&
                (targetField.getText() != null && !targetField.getText().isEmpty()) &&
                (passwordField.getText() != null && !passwordField.getText().isEmpty());
    }
}