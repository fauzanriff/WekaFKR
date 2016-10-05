/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr;

import weka.classifiers.trees.J48;
import weka.core.Instances;
/**
 *
 * @author User
 */
public class BuildClassifier {
    public static void buildClassifier(Instances inst) throws Exception {
        String[] options = new String[1];
        options[0] = "-U";
        J48 tree = new J48();
        tree.setOptions(options);
        tree.buildClassifier(inst);
    }
}
