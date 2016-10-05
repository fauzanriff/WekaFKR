/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.classifier;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.Attribute;
import weka.core.Instance;

/**
 *
 * @author Rahman Adianto
 */
public class MyID3 extends Classifier {
    
    private Instances instances;
    
    public MyID3() {}
    
    @Override
    public void buildClassifier(Instances instances) throws Exception {
        if (!instances.classAttribute().isNominal()) {
            throw new Exception("Class not nominal");
        }
        
        for (int j = 0; j < instances.numAttributes(); j++) {
            Attribute attr = instances.attribute(j);
            if (!attr.isNominal()) {
                throw new Exception("Attribute not nominal");
            }
            
            for (int k = 0; k < instances.numInstances(); k++) {
                Instance inst = instances.instance(k);
                if (inst.isMissing(attr)) {
                    throw new Exception("Missing value");
                }
            }
        }
        
        this.instances = new Instances(instances);
        this.instances.deleteWithMissingClass();
        
        //makeTree(this.instances);
    }
}
