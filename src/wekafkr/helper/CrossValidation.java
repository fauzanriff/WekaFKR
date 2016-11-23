/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.helper;


import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class CrossValidation {
    public static void crossValidation(Instances data, Classifier cls, int folds) throws Exception {
        Evaluation evaluation = new Evaluation(data);
        evaluation.crossValidateModel(cls, data, folds, new Random(1));
        System.out.println(evaluation.toSummaryString());
    }
    
    public static double crossValidationrate(Instances data, Classifier cls, int folds) throws Exception {
        Evaluation evaluation = new Evaluation(data);
        evaluation.crossValidateModel(cls, data, folds, new Random(1));
        return evaluation.pctCorrect();
    }
}