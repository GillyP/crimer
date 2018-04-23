/**
 * Launches crimer
 *
 * @author Alexander "Lex" Adams, Justin Varley
 */

package CrimerGui;

import ArticleParser.Parser;
import Weka.CrimeClassifier;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.http.client.fluent.Request;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Launcher extends Application {

    private static String location;
    private static String fromDate;
    private Scene scene;
    GuiView guiView;


    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI
        SwingUtilities.invokeLater(Launcher::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Use the Java look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Make sure we have nice window decorations
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        JTextField inputLocation = new JTextField();
        JTextField inputFromDate = new JTextField();

        final JComponent[] inputs = new JComponent[]{
                new JLabel("City:"),
                inputLocation,
                new JLabel("Starting date (YYYY-MM-DD):"),
                inputFromDate,
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Crimer", JOptionPane.DEFAULT_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            location = inputLocation.getText();
            fromDate = inputFromDate.getText();
            try {
                generateJS(location, fromDate);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User cancelled / closed the dialog, result = " + result);
        }
    }

    private static void generateJS(String location, String fromDate) throws Exception {

        Parser parse = new Parser(location, fromDate);
        List<String> headlines = parse.getHeadlines();
        CrimeClassifier crimer = new CrimeClassifier(headlines);

        List<List<String>> addresses = parse.getAddresses();
        List<String> classifications = crimer.getClassifications();

        PrintWriter writer = new PrintWriter("crimer/src/CrimerGui/crime_data.js", "UTF-8");

        StringBuilder red = new StringBuilder();
        StringBuilder green = new StringBuilder();
        StringBuilder blue = new StringBuilder();
        StringBuilder yellow = new StringBuilder();
        StringBuilder purple = new StringBuilder();

        red.append("function getRedPoints() {\n" +
                "    return [\n");

        green.append("function getGreenPoints() {\n" +
                "    return [\n");

        blue.append("function getBluePoints() {\n" +
                "    return [\n");

        yellow.append("function getYellowPoints() {\n" +
                "    return [\n");

        purple.append("function getPurplePoints() {\n" +
                "    return [\n");

        for (int i = 0; i < classifications.size(); i++) {
            switch (classifications.get(i)) {
                case "financial":
                    writeGeolocation(i, red, addresses, writer);
                    break;
                case "property":
                    writeGeolocation(i, green, addresses, writer);
                    break;
                case "personal":
                    writeGeolocation(i, blue, addresses, writer);
                    break;
                case "inchoate":
                    writeGeolocation(i, yellow, addresses, writer);
                    break;
                case "statutory":
                    writeGeolocation(i, purple, addresses, writer);
                    break;
            }
        }

        red.append("        new google.maps.LatLng(37.782992, -122.442112)\n" +
                "    ];\n" +
                "}\n");
        green.append("        new google.maps.LatLng(37.782992, -122.442112)\n" +
                "    ];\n" +
                "}\n");
        blue.append("        new google.maps.LatLng(37.782992, -122.442112)\n" +
                "    ];\n" +
                "}\n");
        yellow.append("        new google.maps.LatLng(37.782992, -122.442112)\n" +
                "    ];\n" +
                "}\n");
        purple.append("        new google.maps.LatLng(37.782992, -122.442112)\n" +
                "    ];\n" +
                "}\n");

        writer.println(red.toString());
        writer.println(green.toString());
        writer.println(blue.toString());
        writer.println(yellow.toString());
        writer.println(purple.toString());

        StringBuilder ticker = new StringBuilder();
        for (String headline : headlines) {
            ticker.append(headline);
            ticker.append(" · ");
        }

        writer.print("var tickerText = \"" + ticker.toString().replace("\"", "\\\"").toUpperCase() + "\";");
        writer.close();

        launchCrimer();
    }

    private static void writeGeolocation(int index, StringBuilder color, List<List<String>> addresses, PrintWriter writer) throws IOException {
        for (int j = 0; j < addresses.get(index).size(); j++) {
            try {
                String request = Request.Get("https://maps.googleapis.com/maps/api/geocode/json?address="
                        + addresses.get(index).get(j).replace(" ", "+") +
                        "&key=AIzaSyB06sE1R5EMBA4ysw5m-Gmk3PSlnenKaYE")
                        .connectTimeout(1000)
                        .socketTimeout(1000)
                        .execute().returnContent().asString();
                Object jsonString = Configuration.defaultConfiguration().jsonProvider().parse(request);
                String lat = JsonPath.read(jsonString, "results[0].geometry.location.lat").toString();
                String lng = JsonPath.read(jsonString, "results[0].geometry.location.lng").toString();
                color.append("        new google.maps.LatLng(" + lat + ", " + lng + "),\n");
            } catch (IllegalArgumentException | PathNotFoundException e) {
                // Do something if the address fails?
            }
        }
    }

    private static void launchCrimer() {
        launch();
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