package boilerpipe;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class BoilerpipeDemo {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a city: ");
        String city = sc.nextLine();
        String cityURL = city.replace(" ", "%20");

        System.out.print("Enter a starting date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        Boilerpipe demoPipe = new Boilerpipe(cityURL, date);

        System.out.println();

        System.out.println("Number of articles found: " + demoPipe.getNumArticles());

        System.out.println();

        System.out.println("These are the headlines for " + city + ":");
        List headlines = demoPipe.getHeadlines();
        for (Object headline : headlines)
            System.out.println(headline);

        System.out.println();

        System.out.println("These are the headlines for " + city + ":");
        List urls = demoPipe.getURLs();
        for (Object url : urls)
            System.out.println(url);
    }
}