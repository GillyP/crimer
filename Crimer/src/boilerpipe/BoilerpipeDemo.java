package boilerpipe;

import de.l3s.boilerpipe.BoilerpipeProcessingException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BoilerpipeDemo {

    public static void main(String[] args) throws IOException, BoilerpipeProcessingException {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a city: ");
        String city = sc.nextLine();

        System.out.print("Enter a starting date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        Boilerpipe demoPipe = new Boilerpipe(city, date);

        System.out.println();

        System.out.println("Number of articles found: " + demoPipe.getNumArticles());

        System.out.println();

        System.out.println("These are the headlines for " + city + ":");
        List headlines = demoPipe.getHeadlines();
        for (Object headline : headlines)
            System.out.println(headline);

        System.out.println();

        System.out.println("These are the URLs for " + city + ":");
        List urls = demoPipe.getURLs();
        for (Object url : urls)
            System.out.println(url);

        System.out.println();

        System.out.println("These are the possible addresses for " + city + ":");
        List addresses = demoPipe.getAddresses();
        System.out.println(Arrays.deepToString(addresses.toArray()));
    }
}