package Weka;

import ArticleParser.Parser;

import java.io.IOException;
import java.util.Formatter;

public class ArffGenerator {

    private Formatter form;

    private Parser bp;

    public ArffGenerator() {

    }

    public void generateArff(String fileName) throws IOException {
        try {

            form = new Formatter("crimer.arff");

            form.format("%s%n%s%n%s%n", "@RELATION crimer",
                    "@ATTRIBUTE crimetype {personal,property,inchoate,statutory,financial}",
                "@DATA");

            for(int i = 0; i < bp.getHeadlines().size(); i++) {
                form.format("%s%n", bp.getHeadlines().get(i));
            }



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
