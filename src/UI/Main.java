package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    private final String WINDOW_TITLE = "Medical Record Encryptor";
    private final boolean WINDOW_RESIZABLE = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL xmlUrl = getClass().getResource("sample.fxml");

        Parent root = FXMLLoader.load(xmlUrl);
        FXMLLoader loader = new FXMLLoader(xmlUrl);

        Controller controller = new Controller();

        loader.setController(controller);

        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setResizable(WINDOW_RESIZABLE);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
