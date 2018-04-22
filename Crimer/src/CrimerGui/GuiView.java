package CrimerGui;

import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;

class GuiView extends StackPane {

    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();

    public GuiView(){
        final URL urlGoogleMaps = getClass().getResource("GoogleHeatMap.html");
        webEngine.load(urlGoogleMaps.toExternalForm());

        getChildren().add(webView);
    }
}
