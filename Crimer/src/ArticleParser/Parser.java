/**
 * A parser that retrieves headlines and addresses for a location
 *
 * @author Alexander "Lex" Adams
 */

package ArticleParser;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    /**
     * A list of URLs
     */
    private List<String> urls;
    /**
     * A list of headlines
     */
    private List<String> headlines;
    /**
     * A list of addresses
     */
    private List<List<String>> addresses;
    /**
     * The total number of articles available for parsing
     */
    private int numArticles;
    /**
     * The number of errors encountered while extracting text
     */
    private int numErrors;

    /**
     * Creates a parser object that holds data related to crime
     *
     * @param location A location
     * @param date     A starting date
     * @throws IOException When the http request fails
     */
    public Parser(String location, String date) throws IOException {

        location = location.replace(" ", "%20");

        String URL = "https://newsapi.org/v2/everything?q=" +
                "%22" + location + "%22%20" +
                // words associated with crime
                "AND%20(crime%20OR%20arson%20OR%20murder%20OR%20rape%20OR%20%22grand%20theft%20auto%22%20OR%20prostitution%20" +
                "OR%20theft%20OR%20%22child%20abuse%22%20OR%20%22domestic%20abuse%22%20OR%20conspiracy%20OR%20solicit%20OR%20" +
                "solicitation%20OR%20dui%20OR%20dwi%20OR%20%22alcohol%20to%20minors%22%20OR%20breathalyzer%20OR%20%22refusing%20" +
                "to%20perform%22%20OR%20%22refuse%20to%20perform%22%20OR%20blackmail%20OR%20embezzlement%20OR%20cybercrime%20OR%20" +
                "fraud%20OR%20%22money%20laundering%22%20OR%20%22tax%20evasion%22%20OR%20%22drug%20possession%22%20OR%20%22drug%20" +
                "manufacturing%22%20OR%20trafficking%20OR%20%22owi%22%20OR%20%22reckless%20driving%22%20OR%20%22driving%20on%20a%20" +
                "suspended%20license%22%20OR%20%22hit-and-run%22%20OR%20%22driving%20on%20a%20revoked%20license%22%20OR%20%22public%20" +
                "intoxication%22%20OR%20%22aiding%20and%20abetting%22%20OR%20homicide%20OR%20manslaughter%20OR%20assault%20OR%20" +
                "battery%20OR%20larsony%20OR%20%22hate%20crime%22%20OR%20vandalism%20OR%20perjury%20OR%20trespass%20OR%20speeding%20" +
                "OR%20%22breaking%20and%20entering%22%20OR%20%22child%20porn%22%20OR%20molest%20OR%20%22sexual%20assault%22)" +
                "&from=" + date +
                "&sortBy=relevancy" +
                "&language=en" +
                "&pageSize=100" +
                "&apiKey=a447b403db674cb6b07c26fc11efdcd3";

        parseJSON(Request.Get(URL)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .execute().returnContent().asString());

        extractAddresses();
    }

    /**
     * Creates the default parser object that holds data related to crime
     */
    public Parser() {
        urls = new ArrayList<>();
        headlines = new ArrayList<>();
        addresses = new ArrayList<>();
        numArticles = 0;
        numErrors = 0;
    }

    /**
     * Parses a JSON string for URLs and headlines
     *
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
    public List<String> getURLs() {
        return urls;
    }

    /**
     * @return A list of headlines
     */
    public List<String> getHeadlines() {
        return headlines;
    }

    /**
     * @return A 2D list of addresses
     */
    public List getAddresses() {
        return addresses;
    }

    /**
     * @return The number of articles
     */
    public int getNumArticles() {
        return numArticles;
    }

    /**
     * @return The number of errors encountered while extracting text
     */
    public int getNumErrors() {
        return numErrors;
    }

    /**
     * A helper method that extracts addresses from text using regex
     */
    private void extractAddresses() {
        // Regex to match street addresses within article text
        final Pattern regex = Pattern.compile("\\d\\w* .{5,40} (ALLEY|ALY|ANEX|ANX|ARCADE|ARC|AVENUE|AVE|BAYOU|BYU|BEACH|BCH" +
                "|BEND|BND|BLUFF|BLF|BLUFFS|BLFS|BOTTOM|BTM|BOULEVARD|BLVD|BRANCH|BR|BRIDGE|BRG|BROOK|BRK|BROOKS|BRKS" +
                "|BURG|BG|BURGS|BGS|BYPASS|BYP|CAMP|CP|CANYON|CYN|CAPE|CPE|CAUSEWAY|CSWY|CENTER|CTR|CENTERS|CTRS|CIRCLE" +
                "|CIR|CIRCLES|CIRS|CLIFF|CLF|CLIFFS|CLFS|CLUB|CLB|COMMON|CMN|COMMONS|CMNS|CORNER|COR|CORNERS|CORS|COURSE" +
                "|CRSE|COURT|CT|COURTS|CTS|COVE|CV|COVES|CVS|CREEK|CRK|CRESCENT|CRES|CREST|CRST|CROSSING|XING|CROSSROAD" +
                "|XRD|CROSSROADS|XRDS|CURVE|CURV|DALE|DL|DAM|DM|DIVIDE|DV|DRIVE|DR|DRIVES|DRS|ESTATE|EST|ESTATES|ESTS" +
                "|EXPRESSWAY|EXPY|EXTENSION|EXT|EXTENSIONS|EXTS|FALL|FALLS|FLS|FERRY|FRY|FIELD|FLD|FIELDS|FLDS|FLAT|FLT" +
                "|FLATS|FLTS|FORD|FRD|FORDS|FRDS|FOREST|FRST|FORGE|FRG|FORGES|FRGS|FORK|FRK|FORKS|FRKS|FORT|FT|FREEWAY|FWY" +
                "|GARDEN|GDN|GARDENS|GDNS|GATEWAY|GTWY|GLEN|GLN|GLENS|GLNS|GREEN|GRN|GREENS|GRNS|GROVE|GRV|GROVES|GRVS" +
                "|HARBOR|HBR|HARBORS|HBRS|HAVEN|HVN|HEIGHTS|HTS|HIGHWAY|HWY|HILL|HL|HILLS|HLS|HOLLOW|HOLW|INLET|INLT|ISLAND" +
                "|IS|ISLANDS|ISS|ISLE|JUNCTION|JCT|JUNCTIONS|JCTS|KEY|KY|KEYS|KYS|KNOLL|KNL|KNOLLS|KNLS|LAKE|LK|LAKES|LKS|LAND" +
                "|LANDING|LNDG|LANE|LN|LIGHT|LGT|LIGHTS|LGTS|LOAF|LF|LOCK|LCK|LOCKS|LCKS|LODGE|LDG|LOOP|MALL|MANOR|MNR|MANORS" +
                "|MNRS|MEADOW|MDW|MEADOWS|MDWS|MEWS|MILL|ML|MILLS|MLS|MISSION|MSN|MOTORWAY|MTWY|MOUNT|MT|MOUNTAIN|MTN|MOUNTAINS" +
                "|MTNS|NECK|NCK|ORCHARD|ORCH|OVAL|OVERPASS|OPAS|PARK|PARKS|PARKWAY|PKWY|PARKWAYS|PASS|PASSAGE|PSGE" +
                "|PATH|PIKE|PINE|PNE|PINES|PNES|PLACE|PL|PLAIN|PLN|PLAINS|PLNS|PLAZA|PLZ|POINT|PT|POINTS|PTS|PORT|PRT|PORTS" +
                "|PRTS|PRAIRIE|PR|RADIAL|RADL|RAMPRANCH|RNCH|RAPID|RPD|RAPIDS|RPDS|REST|RST|RIDGE|RDG|RIDGES|RDGS|RIVER|RIV" +
                "|ROAD|RD|ROADS|RDS|ROUTE|RTE|ROW|RUE|RUN|SHOAL|SHL|SHOALS|SHLS|SHORE|SHR|SHORES|SHRS|SKYWAY|SKWY|SPRING|SPG" +
                "|SPRINGS|SPGS|SPURS|SPUR|SQUARE|SQ|SQUARES|SQS|STATION|STA|STRAVENUE|STRA|STREAM|STRM|STREET|ST|STREETS|STS" +
                "|SUMMIT|SMT|TERRACE|TER|THROUGHWAY|TRWY|TRACE|TRCE|TRACK|TRAK|TRAFFICWAY|TRFY|TRAIL|TRL|TRAILER|TRLR|TUNNEL" +
                "|TUNL|TURNPIKE|TPKE|UNDERPASS|UPAS|UNION|UN|UNIONS|UNS|VALLEY|VLY|VALLEYS|VLYS|VIADUCT|VIA|VIEW|VW|VIEWS|VWS" +
                "|VILLAGE|VLG|VILLAGES|VLGS|VILLE|VL|VISTA|VIS|WALK|WALKS|WALL|WAY|WAYS|WELL|WL|WELLS|WLS|Alley|Aly|Anex|Anx" +
                "|Arcade|Arc|Avenue|Ave|Bayou|Byu|Beach|Bch|Bend|Bnd|Bluff|Blf|Bluffs|Blfs|Bottom|Btm|Boulevard|Blvd|Branch" +
                "|Br|Bridge|Brg|Brook|Brk|Brooks|Brks|Burg|Bg|Burgs|Bgs|Bypass|Byp|Camp|Cp|Canyon|Cyn|Cape|Cpe|Causeway|Cswy" +
                "|Center|Ctr|Centers|Ctrs|Circle|Cir|Circles|Cirs|Cliff|Clf|Cliffs|Clfs|Club|Clb|Common|Cmn|Commons|Cmns|Corner" +
                "|Cor|Corners|Cors|Course|Crse|Court|Ct|Courts|Cts|Cove|Cv|Coves|Cvs|Creek|Crk|Crescent|Cres|Crest|Crst" +
                "|Crossing|Xing|Crossroad|Xrd|Crossroads|Xrds|Curve|Curv|Dale|Dl|Dam|Dm|Divide|Dv|Drive|Dr|Drives|Drs|Estate" +
                "|Est|Estates|Ests|Expressway|Expy|Extension|Ext|Extensions|Exts|Fall|Falls|Fls|Ferry|Fry|Field|Fld|Fields|Flds" +
                "|Flat|Flt|Flats|Flts|Ford|Frd|Fords|Frds|Forest|Frst|Forge|Frg|Forges|Frgs|Fork|Frk|Forks|Frks|Fort|Ft|Freeway" +
                "|Fwy|Garden|Gdn|Gardens|Gdns|Gateway|Gtwy|Glen|Gln|Glens|Glns|Green|Grn|Greens|Grns|Grove|Grv|Groves|Grvs|Harbor" +
                "|Hbr|Harbors|Hbrs|Haven|Hvn|Heights|Hts|Highway|Hwy|Hill|Hl|Hills|Hls|Hollow|Holw|Inlet|Inlt|Island|Is|Islands" +
                "|Iss|Isle|Junction|Jct|Junctions|Jcts|Key|Ky|Keys|Kys|Knoll|Knl|Knolls|Knls|Lake|Lk|Lakes|Lks|Land|Landing|Lndg" +
                "|Lane|Ln|Light|Lgt|Lights|Lgts|Loaf|Lf|Lock|Lck|Locks|Lcks|Lodge|Ldg|Loop|Mall|Manor|Mnr|Manors|Mnrs|Meadow|Mdw" +
                "|Meadows|Mdws|Mews|Mill|Ml|Mills|Mls|Mission|Msn|Motorway|Mtwy|Mount|Mt|Mountain|Mtn|Mountains|Mtns|Neck|Nck" +
                "|Orchard|Orch|Oval|Overpass|Opas|Park|Parks|Parkway|Parkways|Pkwy|Pass|Passage|Psge|Path|Pike|Pine|Pne|Pines|Pnes" +
                "|Place|Pl|Plain|Pln|Plains|Plns|Plaza|Plz|Point|Pt|Points|Pts|Port|Prt|Ports|Prts|Prairie|Pr|Radial|Radl|Ramp" +
                "|Ranch|Rnch|Rapid|Rpd|Rapids|Rpds|Rest|Rst|Ridge|Rdg|Ridges|Rdgs|River|Riv|Road|Rd|Roads|Rds|Route|Rte|Row|Rue|Run" +
                "|Shoal|Shl|Shoals|Shls|Shore|Shr|Shores|Shrs|Skyway|Skwy|Spring|Spg|Springs|Spgs|Spur|Spurs|Square|Sq|Squares|Sqs" +
                "|Station|Sta|Stravenue|Stra|Stream|Strm|Street|St|Streets|Sts|Summit|Smt|Terrace|Ter|Throughway|Trwy|Trace|Trce|Track" +
                "|Trak|Trafficway|Trfy|Trail|Trl|Trailer|Trlr|Tunnel|Tunl|Turnpike|Tpke|Underpass|Upas|Union|Un|Unions|Uns|Valley" +
                "|Vly|Valleys|Vlys|Viaduct|Via|View|Vw|Views|Vws|Village|Vlg|Villages|Vlgs|Ville|Vl|Vista|Vis|Walk|Walks|Wall|Way" +
                "|Ways|Well|Wl|Wells|Wls)[\\p{Punct} ]");

        addresses = new ArrayList<>();
        for (String url : urls) {
            List<String> tempAddress = new ArrayList<>();
            try {
                Matcher regexMatcher = regex.matcher(extractText(url));
                while (regexMatcher.find()) {
                    tempAddress.add(regexMatcher.group());
                }
            } catch (MalformedURLException | BoilerpipeProcessingException | IllegalCharsetNameException e) {
                numErrors++;
            }
            addresses.add(tempAddress);
        }
    }

    /**
     * A helper method that extracts text from news articles using boilerpipe
     *
     * @param URL A news article to have text extracted from
     * @return The text of the article
     * @throws BoilerpipeProcessingException When the text fails to process
     * @throws MalformedURLException         When the URL is malformed
     */
    private String extractText(String URL) throws BoilerpipeProcessingException, MalformedURLException {
        URL url = new URL(URL);
        return ArticleExtractor.INSTANCE.getText(url);
    }
}