package Weka;

import java.lang.reflect.AnnotatedType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;

import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;


//@TODO Other attribute
//@TODO save articles, find out which kind of crime

public class CrimerClassifier {

    private Attribute crime;

    private ArrayList attributes;
    private ArrayList classType;

    private Instances dataRaw;


    private Attribute property;
    private Attribute personal;
    private Attribute statutory;
    private Attribute inchoate;
    private Attribute financial;
/*
    private ArrayList attributes;
    private ArrayList classType;

    private Instances dataRaw;*/
/*
    public CrimerClassifier() {
        property = new Attribute("property");
        personal = new Attribute("personal");
        statutory = new Attribute("Statutory");
        inchoate = new Attribute("inchoate");
        financial = new Attribute("financial");


        classType = new ArrayList();
        attributes = new ArrayList();

        attributes.add(property);
        attributes.add(personal);
        attributes.add(statutory);
        attributes.add(inchoate);
        attributes.add(financial);
*
        classType.add("crime");
        attributes.add(new Attribute("class", classType));

        dataRaw = new Instances("TestInstances" , attributes, 0);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
    }*/


    public CrimerClassifier() {

        crime = new Attribute("crime");

        classType = new ArrayList();
        attributes = new ArrayList();

        attributes.add(crime);
       /* attributes.add(property);
        attributes.add(personal);
        attributes.add(statutory);
        attributes.add(inchoate);
        attributes.add(financial);*/

        classType.add("personal");
        classType.add("property");
        classType.add("inchoate");
        classType.add("financial");
        classType.add("statutory");

        attributes.add(new Attribute("class", classType));

        dataRaw = new Instances("TestInstances", attributes, 0);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);

    }


    public Instances createInstance(double crime) {

        dataRaw.clear();
        double[] instanceVal = new double[] {crime, 0};
        dataRaw.add(new DenseInstance(1.0, instanceVal));
        return dataRaw;
    }

    public String classify(Instances inst, String path) {

        String result = "";
        Classifier cls = null;
        try {
            cls = (MultilayerPerceptron) SerializationHelper.read(path);
            result = (String) classType.get((int) cls.classifyInstance(inst.firstInstance()));

        } catch (Exception ex) {
            Logger.getLogger(CrimerClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Instances getInstance() {
        return dataRaw;
    }
}
