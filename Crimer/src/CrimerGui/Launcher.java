/**
 * Launches crimer
 *
 * @author Justin Varley, Alexander "Lex" Adams
 */

package CrimerGui;

import ArticleParser.Parser;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.apache.http.client.fluent.Request;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Launcher {

    private static String location;
    private static String fromDate;

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(Launcher::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        //Use the Java look and feel.
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

        //Make sure we have nice window decorations.
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
            }
        } else {
            System.out.println("User cancelled / closed the dialog, result = " + result);
        }
    }

    private static void generateJS(String location, String fromDate) throws IOException {

        Parser parse = new Parser(location, fromDate);

        List<List<String>> addresses = parse.getAddresses();
        List<String> headlines = parse.getHeadlines();

        PrintWriter writer = new PrintWriter("crimer/src/CrimerGui/crime_data.js", "UTF-8");

        writer.println("function getINSERTCOLORHEREPoints() {\n" +
                "    return [\n" +
                "        new google.maps.LatLng(latCoord, longCoord),");

        for (int i = 0; i < addresses.size(); i++) {
            for (int j = 0; j < addresses.get(i).size(); j++) {
                String request = Request.Get("https://maps.googleapis.com/maps/api/geocode/json?address="
                        + addresses.get(i).get(j).replace(" ", "+") +
                        "&key=AIzaSyB06sE1R5EMBA4ysw5m-Gmk3PSlnenKaYE")
                        .connectTimeout(1000)
                        .socketTimeout(1000)
                        .execute().returnContent().asString();
                Object jsonString = Configuration.defaultConfiguration().jsonProvider().parse(request);

                try {
                    String lat = JsonPath.read(jsonString, "results[0].geometry.location.lat").toString();
                    String lng = JsonPath.read(jsonString, "results[0].geometry.location.lng").toString();
                    writer.println("        new google.maps.LatLng(" + lat + ", " + lng + "),");
                } catch (PathNotFoundException e) {
                    // Do something if the address fails?
                }
            }
        }

        writer.println("" +
                // Google's address to cap off the pair list
                "        new google.maps.LatLng(37.4228775, -122.085133)\n" +
                "    ];\n" +
                "}\n");

        StringBuilder ticker = new StringBuilder();
        for (String headline : headlines) {
            ticker.append(headline);
            ticker.append(" · ");
        }

        writer.print("var tickerText = \"" + ticker.toString().replace("\"", "\\\"").toUpperCase() + "\";");
        writer.close();
    }
}