/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.helper;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
/**
 *
 * @author User
 */
public class BuildClassifier {
    private Classifier tree;
    private Evaluation eval;
    private String pathTest;
    
    public void run(Instances inst) {
        try {
            String[] options = new String[1];
            options[0] = "-U";
            tree = new J48();
            tree.setOptions(options);
            tree.buildClassifier(inst);
        } catch (Exception ex) {
            Logger.getLogger(BuildClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void testing(Instances train, Instances test){
        try {
            eval = new Evaluation(train);
            eval.evaluateModel(tree, test);
            System.out.println(eval.toSummaryString("===Result===\n", false));
        } catch (Exception ex) {
            Logger.getLogger(BuildClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showResult(){
        System.out.println("J48 Result: ");
        System.out.println(tree.toString());
    }
}
