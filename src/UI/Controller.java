package ui;

import business_logic.EncryptionManager;
import models.FormData;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {
    private final String SOURCE_FILE_CHOOSER_TITLE = "Select Source File";
    private final String TARGET_DIRECTORY_CHOOSER_TITLE = "Select Target Directory";

    private final boolean DEFAULT_CHECKBOX_VALUE = false;
    private final String DEFAULT_CHOICEBOX_VALUE = "100";
    private final String DEFAULT_TEXTFIELD_VALUE = "";

    private final String DIALOG_TITLE = "Warning!";

    private final EncryptionManager encryptionManager = new EncryptionManager();

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

    /**
     * Handles the click event for the source file selection button and it
     * disables the button while the file picker is open. If a file was
     * successfully chosen, its path is assigned to the corresponding
     * text field.
     * @param mouseEvent
     */
    @FXML
    private void sourceLocationButtonClicked(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(SOURCE_FILE_CHOOSER_TITLE);

        sourceButton.setDisable(true);

        File file = fileChooser.showOpenDialog(null);
        if (file != null){
            sourceField.setText(file.getAbsolutePath());
        }

        sourceButton.setDisable(false);
    }

    /**
     * Handles the click event for the target directory selection button
     * and it disables the button while the directory picker is open. If a
     * directory was successfully chosen, its path is assigned to the corresponding
     * text field.
     * @param mouseEvent
     */
    @FXML
    private void targetLocationButtonClicked(MouseEvent mouseEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(TARGET_DIRECTORY_CHOOSER_TITLE);

        targetButton.setDisable(true);

        File file = directoryChooser.showDialog(null);
        if (file != null){
            targetField.setText(file.getAbsolutePath());
        }

        targetButton.setDisable(false);
    }

    /**
     * Handles the click event for the encryption button. It checks that
     * all the required fields are filled out before proceeding to encrypt.
     * It handles any exceptions by launching a dialog window for alerting
     * the user. If a file was successfully encrypted, the application's
     * form fields will be cleared/reset.
     * @param mouseEvent
     */
    @FXML
    private void encryptButtonClicked(MouseEvent mouseEvent) {
        if (checkUIFormConstraints()){
            FormData formData = generateFormData();

            try {
                encryptionManager.encrypt(formData);

                clearForm();
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle(DIALOG_TITLE);
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());

                alert.showAndWait();
            }
        }
    }

    /**
     * Handles the click event for the decryption button. It checks that
     * all the required fields are filled out before proceeding to decrypt.
     * It handles any exceptions by launching a dialog window for alerting
     * the user. If a file was successfully decrypted, the application's
     * form fields will be cleared/reset.
     * @param mouseEvent
     */
    @FXML
    private void decryptButtonClicked(MouseEvent mouseEvent) {
        if (checkUIFormConstraints()){
            FormData formData = generateFormData();

            try {
                encryptionManager.decrypt(formData);

                clearForm();
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle(DIALOG_TITLE);
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());

                alert.showAndWait();
            }
        }
    }

    /**
     * This method checks that all the required fields are filled out.
     * @return
     */
    private boolean checkUIFormConstraints(){
        return (sourceField.getText() != null && !sourceField.getText().isEmpty()) &&
                (targetField.getText() != null && !targetField.getText().isEmpty()) &&
                (passwordField.getText() != null && !passwordField.getText().isEmpty());
    }

    /**
     * This method populates a FormData object with the filled out form
     * providing all the user input.
     * @return
     */
    private FormData generateFormData() {
        FormData formData = new FormData();
        formData.setDeleteSourceFile(deleteFileCheck.isSelected());
        formData.setIterationCount(iterationChoice.getValue());
        formData.setClearTextPassword(passwordField.getText());
        formData.setSourceFileLocation(sourceField.getText());
        formData.setTargetDirectoryLocation(targetField.getText());

        return formData;
    }

    /**
     * This method clears/resets the UI form.
     */
    private void clearForm() {
        sourceField.setText(DEFAULT_TEXTFIELD_VALUE);
        targetField.setText(DEFAULT_TEXTFIELD_VALUE);
        passwordField.setText(DEFAULT_TEXTFIELD_VALUE);
        deleteFileCheck.setSelected(DEFAULT_CHECKBOX_VALUE);
        iterationChoice.setValue(DEFAULT_CHOICEBOX_VALUE);
    }
}