package Weka;


import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.core.Instances;
import weka.classifiers.evaluation.Evaluation;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CrimerGenerator {


    public Instances loadDataset(String path) {
        Instances dataset = null;
        try {
            dataset = ConverterUtils.DataSource.read(path);
            if (dataset.classIndex() == -1) {
                dataset.setClassIndex(dataset.numAttributes() - 1);
            }
        } catch (Exception ex) {
            Logger.getLogger(CrimerGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dataset;
    }


    public Classifier buildClassifier(Instances dataSet) {

        NaiveBayesMultinomialText nbmt = new NaiveBayesMultinomialText();

        nbmt.setMinWordFrequency(2);

        try {

            nbmt.buildClassifier(dataSet);

        } catch (Exception ex) {

            Logger.getLogger(CrimerGenerator.class.getName()).log(Level.SEVERE, null, ex);

        }
        return nbmt;
    }

    public String evaluateModel(Classifier model, Instances trainDataSet, Instances testDataSet) {
        Evaluation eval = null;

        try {

            eval = new Evaluation(trainDataSet);
            eval.evaluateModel(model, testDataSet);

        } catch (Exception ex) {

            Logger.getLogger(CrimerGenerator.class.getName()).log(Level.SEVERE, null, ex);

        }
        return eval.toSummaryString("", true);
    }

    public void saveModel(Classifier model, String modelPath) {


        try {

            SerializationHelper.write(modelPath, model);

        } catch (Exception ex) {

            Logger.getLogger(CrimerGenerator.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
}


