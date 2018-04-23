package CrimerGui;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.http.client.fluent.Request;
import java.io.IOException;
import java.io.PrintWriter;

public class GuiMain extends Application {
    private Scene scene;
    GuiView guiView;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String location = "baton+rouge";
        String request = Request.Get("https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "&key=AIzaSyB06sE1R5EMBA4ysw5m-Gmk3PSlnenKaYE")
                .connectTimeout(1000)
                .socketTimeout(1000)
                .execute().returnContent().asString();

        Object jsonString = Configuration.defaultConfiguration().jsonProvider().parse(request);
        String lat = JsonPath.read(jsonString, "results[0].geometry.location.lat").toString();
        String lng = JsonPath.read(jsonString, "results[0].geometry.location.lng").toString();
        PrintWriter writer = new PrintWriter("crimer/src/CrimerGui/crime_data.js", "UTF-8");
        writer.println("var latCoord = " + lat + ";");
        writer.println("var longCoord = " + lng + ";");
        writer.close();
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
