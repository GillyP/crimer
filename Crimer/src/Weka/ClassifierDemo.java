package Weka;

import ArticleParser.Parser;
import java.util.List;
import java.util.Scanner;

public class ClassifierDemo {
    public static void main(String[]args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a city: ");
        String city = sc.nextLine();

        System.out.print("Enter a starting date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        Parser parser = new Parser(city,date);

        List<String> headlines = parser.getHeadlines();

        CrimeClassifier crimer = new CrimeClassifier(headlines);

        for (String crime : crimer.getClassifications())
            System.out.println(crime);
    }
}