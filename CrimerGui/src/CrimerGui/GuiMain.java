package CrimerGui;

import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class GuiMain extends Application {

    private Scene scene;
    MyBrowser myBrowser;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Crimer");

        myBrowser = new MyBrowser();
        scene = new Scene(myBrowser, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    class MyBrowser extends Region{

        HBox toolbar;

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        public MyBrowser(){

            final URL urlGoogleMaps = getClass().getResource("GoogleHeatMap.html");
            webEngine.load(urlGoogleMaps.toExternalForm());

            getChildren().add(webView);

        }
    }
}
