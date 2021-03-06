/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.controller;

import java.util.Scanner;
import weka.core.Attribute;
import weka.core.Instances;
import wekafkr.classifier.MyC45;
import wekafkr.classifier.MyClassifier;
import wekafkr.classifier.PercentageSplit;
import wekafkr.classifier.MyID3;
import wekafkr.helper.AttributeManager;
import wekafkr.helper.BuildClassifier;
import wekafkr.helper.CrossValidation;
import wekafkr.helper.FileManager;
import wekafkr.helper.FilterManager;

/**
 *
 * @author fauzanrifqy
 */
public class Menu {
    
    private final String VERSION = "0.1";
    
    private int selection;
    private double percentage;
    private String filename;
    private Instances instances;
    private FileManager fileManager;
    private AttributeManager attrManager;
    private FilterManager filterManager;
    private BuildClassifier buildClassifier;
    private MyClassifier myClassifier = new MyClassifier() {};
    
    public Menu(){
        fileManager = new FileManager();
        filename = "weather_numeric.arff";
        instances = null;
    }
    
    public boolean stopped() {
        return (selection == -1);
    }
    
    public void showVersioning(){
        System.out.print("#####WEKA FKR v"+VERSION+"#####\n\n");
    }
    
    public void askSelection(){
        showVersioning();
        System.out.print("-1 Terminate \n");
        System.out.print("1 Load File\n");
        
        if(instances != null){
            System.out.print("2 Show Attributes\n");
            System.out.print("3 Filter\n");
            System.out.print("4 Build Classifier\n");
            System.out.print("5 Testing Model\n");
            System.out.print("6 Save Model\n");
            System.out.print("7 Load Model\n");          
            System.out.print("8 Custom Classifier\n");
            System.out.print("9 Cross-Validation\n");
            System.out.print("10 Percentage Split\n");
        
            System.out.print("Relation: "+instances.relationName()+"\n");
            System.out.print("Instances: "+Integer.toString(instances.numInstances())+"\n");
            System.out.print("Atributes: "+Integer.toString(instances.numAttributes())+"\n");
        }
        
        System.out.print("\nYour input: ");
        
        Scanner in = new Scanner(System.in);
        selection = in.nextInt();
    }
    
    public void goToSelection() throws Exception{
        showVersioning();
        switch(selection){
            case -1: //terminate program
                break;
            case 1: //load file
                instances = fileManager.openFile(filename); 
                
                if (instances != null) {
                    instances.setClassIndex(instances.numAttributes() - 1);
                    attrManager = new AttributeManager(instances);
                    filterManager = new FilterManager();
                    buildClassifier = new BuildClassifier();
                }else{
                    System.out.println("File path isn't recognize, please try again.");
                }
                break;
            case 2:
                attrManager.showAttributes();
                break;
            case 3:
                System.out.print("Please input sample sice percent : ");
                
                Scanner in = new Scanner(System.in);
                percentage = in.nextInt();                
                
                instances = filterManager.filterResample(instances, percentage);
                break;
            case 4:
                buildClassifier.run(instances);
                buildClassifier.showResult();
                break;
            case 5:
                Instances testInstances = fileManager.openFile(filename);
                
                if (testInstances != null) {
                    testInstances.setClassIndex(testInstances.numAttributes() - 1);
                }else{
                    System.out.println("File path isn't recognized, please try again.");
                }
                buildClassifier.testing(instances, testInstances);
                break;
            case 8:
                myClassifier.run(instances);
                break;
            case 9:
                System.out.print("choose classifier (1 = MyID3, 2 = MyC45) : ");
                in = new Scanner(System.in);
                int choice = in.nextInt();
                
                System.out.print("Folds : ");
                in = new Scanner(System.in);
                int folds = in.nextInt();                
                
                switch(choice){
                    case 1 : 
                        CrossValidation.crossValidation(instances, myClassifier.myid3, folds);
                        break;
                    case 2 :
                        CrossValidation.crossValidation(instances, myClassifier.myc45, folds);
                        break;
                    default:
                        break;
                }
                break;
            case 10:
                System.out.print("choose classifier (1 = MyID3, 2 = MyC45) : ");
                in = new Scanner(System.in);
                choice = in.nextInt();
                
                System.out.print("Percentage : ");
                in = new Scanner(System.in);
                int percentage = in.nextInt();                
                
                switch(choice){
                    case 1 : 
                        PercentageSplit.percentageSplit(instances, myClassifier.myid3);
                        break;
                    case 2 :
                        PercentageSplit.percentageSplit(instances, myClassifier.myc45);
                        break;
                    default:
                        break;
                }
                break;
            default:
                System.out.println("Please input your selection between number listed above.");
                break;
        }
    }
}
