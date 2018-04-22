package CrimerGui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GuiMain extends Application {

    private Scene scene;
    GuiView guiView;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Crimer");

        guiView = new GuiView();
        scene = new Scene(guiView, 1000, 700);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
