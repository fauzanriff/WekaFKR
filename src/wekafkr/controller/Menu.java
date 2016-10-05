/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.controller;

import java.util.Scanner;
import weka.core.Attribute;
import weka.core.Instances;
import wekafkr.helper.AttributeManager;
import wekafkr.helper.FileManager;
import wekafkr.helper.FilterManager;

/**
 *
 * @author fauzanrifqy
 */
public class Menu {
    
    private final String VERSION = "0.1";
    
    private int selection;
    private static Instances instances;
    private static FileManager fileManager;
    private static AttributeManager attrManager;
    private static FilterManager filterManager;
    
    public Menu(){
        fileManager = new FileManager();
    }
    
    public void showVersioning(){
        System.out.print("#####WEKA FKR v"+VERSION+"#####\n\n");
    }
    
    public void askSelection(){
        showVersioning();
        System.out.print("1. Load File\n");
        if(attrManager != null){
            System.out.print("2. Show Attributes\n");
        }
        if(filterManager != null){
            System.out.print("3. Filter\n");
        }
        System.out.print("4. Build Classifier\n");
        System.out.print("5. Testing Model\n\n");
        System.out.print("6. Save Model\n");
        System.out.print("7. Load Model\n\n");
        
        if(instances != null){
            System.out.print("Relation: "+instances.relationName()+"\n");
            System.out.print("Instances: "+Integer.toString(instances.numInstances())+"\n");
            System.out.print("Atributes: "+Integer.toString(instances.numAttributes())+"\n\n");
        }
        
        System.out.print("Your input: ");
        
        Scanner in = new Scanner(System.in);
        selection = in.nextInt();
    }
    
    public void goToSelection(){
        switch(selection){
            case 1:
                showVersioning();
                instances = fileManager.openFile("data/weather.arff");
                instances.setClassIndex(instances.numAttributes() - 1);
                attrManager = new AttributeManager(instances);
                filterManager = new FilterManager();
                break;
            case 2:
                showVersioning();
                attrManager.showAttributes();
                attrManager.selectAttributes();
                break;
            case 3:
                showVersioning();
                instances = filterManager.filterResample(instances, 50.0);
                break;
            default:
                System.out.print("Please input your selection between number listed above.");
                break;
        }
    }
}
