/**
 * Classifies crime based off of headlines
 *
 * @author Alexander "Lex" Adams, Gil Platt
 */

package Weka;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CrimeClassifier {

    private ArrayList<String> classifications;

    public CrimeClassifier(List<String> headlines) throws Exception {
        crimerARFF(headlines);
        generateModel();
        siftThroughARFF();
    }

    public List<String> getClassifications() {
        return classifications;
    }

    private void siftThroughARFF() throws IOException {

        Scanner inFile = new Scanner(new FileReader("crimer/data/weka/crimer.arff"));

        classifications = new ArrayList<>();
        while (inFile.hasNextLine()) {
            String nextToken = inFile.nextLine();
            if (nextToken.contains("@attribute"))
                inFile.nextLine();
            if (nextToken.contains("financial"))
                classifications.add("financial");
            else if (nextToken.contains("property"))
                classifications.add("property");
            else if (nextToken.contains("personal"))
                classifications.add("personal");
            else if (nextToken.contains("inchoate"))
                classifications.add("inchoate");
            else if (nextToken.contains("statutory"))
                classifications.add("statutory");
        }
    }

    private void generateModel() throws Exception {

        BufferedReader breader;
        breader = new BufferedReader(new FileReader("crimer/data/weka/ground_truth.arff"));
        Instances trainSet = new Instances(breader);
        trainSet.setClassIndex(trainSet.numAttributes() - 1);

        breader = new BufferedReader(new FileReader("crimer/data/weka/crimer.arff"));
        Instances testSet = new Instances(breader);
        testSet.setClassIndex(testSet.numAttributes() - 1);

        breader.close();

        MultilayerPerceptron mp = new MultilayerPerceptron();
        mp.buildClassifier(trainSet);

        Instances labeled = new Instances(testSet);

        for (int i = 0; i < testSet.numInstances(); i++) {
            double clsLabel = mp.classifyInstance(testSet.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("crimer/data/weka/crimer.arff"));
        writer.write(labeled.toString());

        writer.close();

    }

    private void crimerARFF(List<String> headlines) throws IOException {

        PrintWriter crimeWriter = new PrintWriter("crimer/data/weka/crimer.arff", "UTF-8");
        crimeWriter.write("@RELATION crimer");
        crimeWriter.println();
        crimeWriter.write("@ATTRIBUTE crimetype string");
        crimeWriter.println();
        crimeWriter.write("@ATTRIBUTE class {personal,property,inchoate,statutory,financial}");
        crimeWriter.println();
        crimeWriter.write("@DATA");
        crimeWriter.println();

        for (String headline : headlines)
            try {
            crimeWriter.format("'%s',?%n", headline.replace("'", "\\'"));
            } catch (NullPointerException e) {
            crimeWriter.println("'ERROR: NULL HEADLINE',?");
        }
        crimeWriter.close();
    }
}