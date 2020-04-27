package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class Main extends Application {

    private final String WINDOW_TITLE = "Medical Record Encryptor";
    private final boolean WINDOW_RESIZABLE = false;

    /**
     * Sets up the starting point of the UI application by loading the respective
     * .fxml file and adding a controller for the components used.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
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

    /**
     * The starting point of the program.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
