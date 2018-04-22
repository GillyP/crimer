package boilerpipe;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import org.apache.http.client.fluent.Request;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A text extractor using Boilerpipe
 */

public class Boilerpipe {

    private List<String> urls = new ArrayList<>();
    private List<String> headlines = new ArrayList<>();
    private int numArticles;

    /**
     * @param location A city location
     * @param date A starting date
     * @throws IOException When http request fails
     */
    public Boilerpipe(String location, String date) throws IOException {

        String URL = "https://newsapi.org/v2/everything?q=" +
                "%22" + location + "%22%20" +
                // words associated with crime
                "AND%20(crime%20OR%20arson%20OR%20murder%20OR%20rape%20OR%20%22grand%20theft%20auto%22%20OR%20prostitution%20OR%20theft%20OR%20%22child%20abuse%22%20OR%20%22domestic%20abuse%22%20OR%20conspiracy%20OR%20solicit%20OR%20solicitation%20OR%20dui%20OR%20dwi%20OR%20%22alcohol%20to%20minors%22%20OR%20breathalyzer%20OR%20%22refusing%20to%20perform%22%20OR%20%22refuse%20to%20perform%22%20OR%20blackmail%20OR%20embezzlement%20OR%20cybercrime%20OR%20fraud%20OR%20%22money%20laundering%22%20OR%20%22tax%20evasion%22%20OR%20%22drug%20possession%22%20OR%20%22drug%20manufacturing%22%20OR%20trafficking%20OR%20%22owi%22%20OR%20%22reckless%20driving%22%20OR%20%22driving%20on%20a%20suspended%20license%22%20OR%20%22hit-and-run%22%20OR%20%22driving%20on%20a%20revoked%20license%22%20OR%20%22public%20intoxication%22%20OR%20%22aiding%20and%20abetting%22%20OR%20homicide%20OR%20manslaughter%20OR%20assault%20OR%20battery%20OR%20larsony%20OR%20%22hate%20crime%22%20OR%20vandalism%20OR%20perjury%20OR%20trespass%20OR%20speeding%20OR%20%22breaking%20and%20entering%22%20OR%20%22child%20porn%22%20OR%20molest%20OR%20%22sexual%20assault%22)" +
                "&from=" +
                date +
                "&sortBy=relevancy" +
                "&language=en" +
                "&pageSize=100" +
                "&apiKey=a447b403db674cb6b07c26fc11efdcd3";

        parseJSON(Request.Get(URL)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .execute().returnContent().asString());
    }

    /**
     * @param jsonInput A string containing JSON
     */
    private void parseJSON(String jsonInput) {
        Object jsonString = Configuration.defaultConfiguration().jsonProvider().parse(jsonInput);
        urls = JsonPath.read(jsonString, "$..url");
        headlines = JsonPath.read(jsonString, "$..title");
        numArticles = JsonPath.read(jsonString, "$.totalResults");
    }

    /**
     * @return A list of URLs
     */
    public List getURLs() {
        return urls;
    }

    /**
     * @return A list of headlines
     */
    public List getHeadlines() {
        return headlines;
    }

    /**
     * @return The number of articles
     */
    public int getNumArticles() {
        return numArticles;
    }

    /**
     * @param URL A URL for use in boilerpipe
     * @return The article's text
     * @throws BoilerpipeProcessingException When there are errors in the text processing
     * @throws MalformedURLException When the URL is malformed
     */
    public String extractText(String URL) throws BoilerpipeProcessingException, MalformedURLException {
        URL url = new URL(URL);
        return ArticleExtractor.INSTANCE.getText(url);
    }
}