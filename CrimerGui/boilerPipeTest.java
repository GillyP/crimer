import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.DefaultExtractor;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import org.xml.sax.InputSource;
import java.io.InputStream;
import java.net.URL;


public class boilerPipeTest {
    public static void main(final String[] args) throws Exception {
        URL url;
        url = new URL("INSERT FULL URL HERE");

        final InputStream urlStream = url.openStream();
        final InputSource is = new InputSource(urlStream);

        final BoilerpipeSAXInput input = new BoilerpipeSAXInput(is);
        final TextDocument doc = input.getTextDocument();
        urlStream.close();

//         System.out.println(DefaultExtractor.INSTANCE.getText(doc));
        System.out.println(ArticleExtractor.INSTANCE.getText(doc));
    }
}
