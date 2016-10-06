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
import weka.core.Utils;

/**
 *
 * @author Rahman Adianto
 */
public class MyID3 extends Classifier {
    
    private Instances instances;
    
    /* The node's successors. */ 
    private MyID3[] successors;
    
    /* Class attribute of dataset. */
    private Attribute classAttribute;

    /* Attribute for splitting. */
    private Attribute splitAttribute;

    /* Class value if node is leaf */
    private double classValue;

    /* Class distribution if node is leaf. */
    private double[] distribution;
    
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
        
        buildTree(this.instances);
    }
    
    public void buildTree(Instances instances) throws Exception {
        
        // Basis
        if (instances.numInstances() == 0) {
            return;
        }
        
        // Recursive
        double[] informationGains = new double[instances.numAttributes()];
        for (int i = 0; i < instances.numAttributes(); i++) {
            Attribute attribute = instances.attribute(i);
            // Check not class Attribute
            if (instances.classIndex() != attribute.index()) {
                informationGains[attribute.index()] = informationGain(attribute, instances);
            }
        }
        
        // Choose best node
        splitAttribute = instances.attribute(Utils.maxIndex(informationGains));
        
        if (informationGains[splitAttribute.index()] == 0) { // leaf
            splitAttribute = null;
            distribution = new double[instances.numClasses()];
            for (int i = 0; i < instances.numInstances(); i++) {
                int m_class = (int) instances.instance(i).value(instances.classAttribute());
                distribution[m_class]++;
            }
            
            Utils.normalize(distribution);
            classValue = Utils.maxIndex(distribution);
            classAttribute = instances.classAttribute();
        }
        else {
            // Split data
            Instances[] data = splitData(splitAttribute, instances);
            
            // Define successors
            successors = new MyID3[data.length];
            
            for (int i = 0; i < data.length; i++) {
                successors[i] = new MyID3();
                successors[i].buildClassifier(data[i]);
            }
        }
    }
    
    public double entropy(Instances instances) {
        double entropy = 0;
    
        // Count number of instance in some class
        double[] classCount = new double[instances.numClasses()];
        for (int i = 0; i < instances.numInstances(); i++) {
            int temp = (int) instances.instance(i).classValue();
            classCount[temp]++;
        }
        
        // Calculate entropy
        for (int i = 0; i < instances.numClasses(); i++) {
            if (classCount[i] > 0) {
                double temp = classCount[i] / instances.numInstances();
                entropy -= temp * Utils.log2(temp);
            }
        }
        
        return entropy;
    }
    
    public double informationGain(Attribute attribute, Instances instances) {
        double informationGain = entropy(instances);
        
        // Split data base on Anttribute
        Instances[] splitDatabyAttribute = splitData(attribute, instances);
        
        // Calculate Information gain
        for (int i = 0; i < splitDatabyAttribute.length; i++) {
            if (splitDatabyAttribute[i].numInstances() > 0) {
                double numInstancesAttribute = (double) splitDatabyAttribute[i].numInstances();
                double numInstances = (double) instances.numInstances();
                
                informationGain -= (numInstancesAttribute / numInstances) * entropy(splitDatabyAttribute[i]);
            }
        }
        
        return informationGain;
    }
    
    public Instances[] splitData(Attribute attribute, Instances instances) {
        Instances[] split = new Instances[attribute.numValues()];
        
        for (int i = 0; i < attribute.numValues(); i++) {
            split[i] = new Instances(instances, instances.numInstances());
        }

        for (int i = 0; i < instances.numInstances(); i++) {
            int temp = (int) instances.instance(i).value(attribute);
            split[temp].add(instances.instance(i));
        }
        
        return split;
    }
    
     public double classifyInstance(Instance instance){
        if(splitAttribute == null) { // leaf
            return classValue;
        }
        else { // recursive
            return successors[(int) instance.value(splitAttribute)].classifyInstance(instance);
        }
    }
     
    public String toString() {
        return "Print tree :D";
    }
}
