/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.classifier;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

/**
 *
 * @author fauzanrifqy
 */
public class MyC45 extends Classifier {
    
    /* The node's successors. */ 
    private MyC45[] successors;
    
    /* Class attribute of dataset. */
    private Attribute classAttribute;

    /* Attribute for splitting. */
    private Attribute splitAttribute;

    /* Class value if node is leaf */
    private double classValue;

    /* Class distribution if node is leaf. */
    private double[] distribution;
  
    private double numericAttThreshold;
    
    public MyC45 head, parent;
    
    public MyC45() {
        head = this;
    }

    public MyC45(MyC45 head, MyC45 parent) {
        this.head = head;
        this.parent = parent;
    }
    
    @Override
    public void buildClassifier(Instances i) throws Exception {
        if (!i.classAttribute().isNominal()) {
            throw new Exception("Class not nominal");
        }

        //penanganan missing value
        for (int j = 0; j < i.numAttributes(); j++) {
            Attribute attr = i.attribute(j);
            for (int k = 0; k < i.numInstances(); k++) {
                Instance inst = i.instance(k);
                if (inst.isMissing(attr)) {
                    inst.setValue(attr, fillMissingValue(i, attr));
                    //bisa dituning lagi performancenya
                }
            }
        }

        i = new Instances(i);
        i.deleteWithMissingClass();
    }

    public double classifyInstance(Instance instance) {
        if (splitAttribute == null) {
            return classValue;
        } else {
            if (splitAttribute.isNominal()) {
                return successors[(int) instance.value(splitAttribute)].classifyInstance(instance);
            } else if (splitAttribute.isNumeric()) {
                if (instance.value(splitAttribute) < numericAttThreshold) {
                    return successors[0].classifyInstance(instance);
                } else {
                    return successors[1].classifyInstance(instance);
                }
            } else {
                return -1;
            }
        }
    }

    public void prune(Instances i) throws Exception {
        if (successors != null) {
            for (int a = 0; a < successors.length; a++) {
                System.out.println("a " + a);
                successors[a].prune(i);
                if (parent != null) {
                    if (calculateErrorPrune(i, a)) {
                        break;
                    };
                }

            }
        }
    }

    public boolean calculateErrorPrune(Instances i, int order) throws Exception {
        double before, after;
        before = PercentageSplit.percentageSplitRate(i, head);
        //MyC45 temp = this.parent.successors[order];
        Attribute temp = this.parent.splitAttribute;
        this.parent.splitAttribute = null;
        double maxafter = 0;
        double maxclass = -1;
        for (int x = 0; x < i.numClasses(); x++) {
            this.parent.classValue = (double) x;
            after = PercentageSplit.percentageSplitRate(i, head);
            if (after > maxafter) {
                maxclass = x;
                maxafter = after;
            }
        }

        this.parent.classValue = maxclass;

        //this.parent.successors[order] = null;
        if (before >= maxafter) {
            this.parent.splitAttribute = temp;
            return false;
        } else {
            System.out.println("prune!!!");
            return true;
        }
    }

    public double fillMissingValue(Instances i, Attribute att) {
        int[] jumlahvalue = new int[att.numValues()];
        for (int k = 0; k < i.numInstances(); k++) {
            jumlahvalue[(int) i.instance(k).value(att)]++;
        }
        return jumlahvalue[Utils.maxIndex(jumlahvalue)];
    }

    public double computeEntropy(Instances inst) {
        double[] classCount = new double[inst.numClasses()];
        for (int i = 0; i < inst.numInstances(); i++) {
            int temp = (int) inst.instance(i).classValue();
            classCount[temp]++;
        }
        double entropy = 0;
        for (int i = 0; i < inst.numClasses(); i++) {
            if (classCount[i] > 0) {
                entropy -= classCount[i] * Utils.log2(classCount[i] / inst.numInstances());
            }
        }
        entropy /= (double) inst.numInstances();
        return entropy;
    }

    public double computeInformationGain(Instances inst, Attribute attr) {
        double gain = computeEntropy(inst);
        Instances[] split = splitData(inst, attr);
        for (int i = 0; i < attr.numValues(); i++) {
            if (split[i].numInstances() > 0) {
                gain -= ((double) split[i].numInstances() / (double) inst.numInstances()) * computeEntropy(split[i]);
            }
        }
        return gain;
    }

    public double computeInformationGainContinous(Instances inst, Attribute attr, double threshold) {
        double gain = computeEntropy(inst);
        Instances[] split = splitDataContinous(inst, attr, threshold);
        for (int i = 0; i < 2; i++) {
            if (split[i].numInstances() > 0) {
                gain -= ((double) split[i].numInstances() / (double) inst.numInstances()) * computeEntropy(split[i]);
            }
        }
        return gain;
    }

    public Instances[] splitData(Instances inst, Attribute attr) {
        Instances[] split = new Instances[attr.numValues()];
        for (int i = 0; i < attr.numValues(); i++) {
            split[i] = new Instances(inst, inst.numInstances());
        }

        for (int i = 0; i < inst.numInstances(); i++) {
            int temp = (int) inst.instance(i).value(attr);
            split[temp].add(inst.instance(i));
        }

        return split;
    }

    public Instances[] splitDataContinous(Instances inst, Attribute attr, double threshold) {
        Instances[] split = new Instances[2];
        for (int i = 0; i < 2; i++) {
            split[i] = new Instances(inst, inst.numInstances());
        }

        for (int i = 0; i < inst.numInstances(); i++) {
            double temp = inst.instance(i).value(attr);
            if (temp < threshold) {
                split[0].add(inst.instance(i));
            } else {
                split[1].add(inst.instance(i));
            }
        }

        return split;
    }
}
