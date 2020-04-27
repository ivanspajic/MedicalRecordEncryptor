package ui;

import business_logic.EncryptionManager;
import models.FormData;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

public class Controller {
    private final String SOURCE_FILE_CHOOSER_TITLE = "Select Source File";
    private final String TARGET_DIRECTORY_CHOOSER_TITLE = "Select Target Directory";

    private final boolean DEFAULT_CHECKBOX_VALUE = false;
    private final String DEFAULT_CHOICEBOX_VALUE = "100";
    private final String DEFAULT_TEXTFIELD_VALUE = "";

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

    @FXML
    private void encryptButtonClicked(MouseEvent mouseEvent) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException {
        if (checkUIFormConstraints()){
            FormData formData = generateFormData();

            encryptionManager.encrypt(formData);

            clearForm();
        }
    }

    @FXML
    private void decryptButtonClicked(MouseEvent mouseEvent) throws BadPaddingException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchProviderException, InvalidKeyException, InvalidKeySpecException {
        if (checkUIFormConstraints()){
            FormData formData = generateFormData();

            encryptionManager.decrypt(formData);

            clearForm();
        }
    }

    private boolean checkUIFormConstraints(){
        return (sourceField.getText() != null && !sourceField.getText().isEmpty()) &&
                (targetField.getText() != null && !targetField.getText().isEmpty()) &&
                (passwordField.getText() != null && !passwordField.getText().isEmpty());
    }

    private FormData generateFormData() {
        FormData formData = new FormData();
        formData.setDeleteSourceFile(deleteFileCheck.isSelected());
        formData.setIterationCount(iterationChoice.getValue());
        formData.setClearTextPassword(passwordField.getText());
        formData.setSourceFileLocation(sourceField.getText());
        formData.setTargetDirectoryLocation(targetField.getText());

        return formData;
    }

    private void clearForm() {
        sourceField.setText(DEFAULT_TEXTFIELD_VALUE);
        targetField.setText(DEFAULT_TEXTFIELD_VALUE);
        passwordField.setText(DEFAULT_TEXTFIELD_VALUE);
        deleteFileCheck.setSelected(DEFAULT_CHECKBOX_VALUE);
        iterationChoice.setValue(DEFAULT_CHOICEBOX_VALUE);
    }
}