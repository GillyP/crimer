package Weka;

import ArticleParser.Parser;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Debug;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

import java.io.*;
import java.text.Normalizer;
import java.util.*;

public class ModelMain {

    private MultilayerPerceptron mp;
    private List<String> headLines;

    private Instances labeled;
    private Instances testSet;
    private Instances trainSet;

    private ArrayList<String> classifications = new ArrayList<>();
    //@TODO have console print out type of crime with boiler pipe


    public static void main(String args[]) throws Exception {
        ModelMain mm = new ModelMain();
       mm.crimerArff();
       // mm.generateModel();
        mm.siftThroughArff();


       /*for(int i = 0; i < mm.getClassifications().size(); i++) {
           System.out.println(mm.getClassifications().get(i));
       }*/

    }

    public ArrayList<String> getClassifications() {
        return classifications;
    }

    public void siftThroughArff() throws IOException {

        //ArrayList<String> classifications = new ArrayList<>();


        Scanner inFile = new Scanner(new FileReader("crimer/data/crimer.arff"));

        while(inFile.hasNextLine()) {
            String nextToken = inFile.nextLine();
            if(nextToken.contains("@attribute"))
                inFile.nextLine();
            if(nextToken.contains("financial"))
                classifications.add("financial");
            else if(nextToken.contains("property"))
                classifications.add("property");
            else if(nextToken.contains("personal"))
                classifications.add("personal");
            else if(nextToken.contains("inchoate"))
                classifications.add("inchoate");
            else if(nextToken.contains("statutory"))
                classifications.add("statutory");
        }
/*
        while (inFile.hasNextLine()) {
            /*if(inFile.nextLine().contains("financial")) {
                classifications.add("financial");
            }
            else if(inFile.nextLine().contains("personal")) {
                classifications.add("personal");
            }
            else if(inFile.nextLine().contains("property")) {
                classifications.add("property");
            }
            else if(inFile.nextLine().contains("statutory")) {
                classifications.add("statutory");
            }
            else if(inFile.nextLine().contains("inchoate")) {
                classifications.add("inchoate");
            } else
                inFile.nextLine();
        }
        inFile.close();*/

    }
    /*
    public ModelMain(List headLines) {
       headLines = this.headLines;
    }*/


   /* public ArrayList<String> getClassifications() {
        return classifications;
    }*/

    public Classifier buildMultilayerPerceptron(Instances dataSet) {
        try {
            mp = new MultilayerPerceptron();
            mp.buildClassifier(dataSet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return mp;
    }

    public Instances trainSet(BufferedReader breader) {
        try {
           trainSet = new Instances(breader);
           trainSet.setClassIndex(trainSet.numAttributes() - 1);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return trainSet;
    }




    public Instances testSet(BufferedReader breader) {
        try {
            testSet = new Instances(breader);
            testSet.setClassIndex(testSet.numAttributes() - 1);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return testSet;
    }





    public void generateModel() throws Exception {
/*
        BufferedReader bufferedReader1 = null;
        BufferedReader bufferedReader2 = null;


        bufferedReader1 = new BufferedReader(new FileReader("C:/Users/Gil Platt/Downloads/Crimer_Ground_Truth.arff"));
       // Instances testSet = testSet(bufferedReader);

        bufferedReader2 = new BufferedReader(new FileReader("C:/Users/Gil Platt/Desktop/crimer.arff"));
        //Instances trainSet = trainSet(bufferedReader);

       // bufferedReader.close();

        buildMultilayerPerceptron(trainSet(bufferedReader2));
        labeled = new Instances(testSet(bufferedReader1));

        for(int i = 0; i < testSet(bufferedReader1).numInstances(); i++) {
            double clsLabel = mp.classifyInstance(testSet(bufferedReader1).instance(i));
            labeled.instance(i).setClassValue(clsLabel);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/Gil Platt/Desktop/GangGang.arff"));
        writer.write(labeled.toString());

        writer.close();

        bufferedReader1.close();
        bufferedReader2.close();*/

        //PrintWriter crimer = arffGenerator();

        BufferedReader breader = null;
        breader = new BufferedReader(new FileReader("crimer/data/ground_truth.arff"));
        trainSet = new Instances(breader);
        trainSet.setClassIndex(trainSet.numAttributes() - 1);

        breader = new BufferedReader(new FileReader("crimer/data/crimer.arff"));
        testSet = new Instances(breader);
        testSet.setClassIndex(testSet.numAttributes() - 1);

        breader.close();

        mp = new MultilayerPerceptron();
        mp.buildClassifier(trainSet);

        labeled = new Instances(testSet);

        for(int i = 0; i < testSet.numInstances(); i++) {
            double clsLabel = mp.classifyInstance(testSet.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("crimer/data/crimer.arff"));
        writer.write(labeled.toString());

        writer.close();

    }


    public void crimerArff() throws IOException{

        //File crimerFile = new File("C:/Users/Gil Platt/Desktop/crimerFile.arff", "UTF-8");
        PrintWriter crimeWriter = new PrintWriter("crimer/data/crimer.arff", "UTF-8");
        crimeWriter.write("@RELATION crimer");
        crimeWriter.println();
        crimeWriter.write("@ATTRIBUTE crimetype string");
        crimeWriter.println();
        crimeWriter.write("@ATTRIBUTE class {personal,property,inchoate,statutory,financial}");
        crimeWriter.println();
        crimeWriter.write("@DATA");
        crimeWriter.println();

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a city: ");
        String city = sc.nextLine();

        System.out.print("Enter a starting date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        Parser demoPipe = new Parser(city, date);

        List<String> headlines = demoPipe.getHeadlines();

        for(int i = 0; i < headlines.size(); i++) {
            String headline = headlines.get(i).replace("'", "\\'");
            //headline = headlines.get(i).replace("’", "\\'");
            crimeWriter.format("'%s',?%n", headline);
        }

        crimeWriter.close();
        //return crimerFile;
    }
}
