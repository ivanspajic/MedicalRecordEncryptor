package UI;

import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

public class Controller {
    private boolean deleteFile = false;

    public Controller() {}

    public void sourceLocationButtonClicked(MouseEvent mouseEvent) {
        System.out.println("source button clicked");
    }

    public void targetLocationButtonClicked(MouseEvent mouseEvent) {
        System.out.println("target button clicked");
    }

    public void deleteSourceFileCheckBoxClicked(MouseEvent mouseEvent) {
        CheckBox checkBox = (CheckBox) mouseEvent.getSource();
        deleteFile = checkBox.isSelected();

        System.out.println("checkbox " + deleteFile);
    }

    public void encryptButtonClicked(MouseEvent mouseEvent){
        System.out.println("encrypt button clicked");
    }

    public void decryptButtonClicked(MouseEvent mouseEvent){
        System.out.println("decrypt button clicked");
    }
}