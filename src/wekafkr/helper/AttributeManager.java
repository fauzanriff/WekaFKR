/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.helper;

import java.util.Scanner;
import weka.core.Attribute;
import weka.core.AttributeStats;
import weka.core.Instances;

/**
 *
 * @author fauzanrifqy
 */
public class AttributeManager {
    
    private final int TYPE_NUMERIC = 0;
    private final int TYPE_NOMINAL = 1;
    
    private int selection;
    private int selectedAttr;
    private Instances instances;
    
    public AttributeManager(Instances in){
        instances = in;
    }
    
    public void deleteAttributes(int input){
        instances.deleteAttributeAt(input);
    }
    
    public void selectAttributes(){
        System.out.print("-1. Back\n\n");
        
        System.out.print("Select attributes: ");
        
        Scanner in = new Scanner(System.in);
        selection = in.nextInt();
        selectedAttr = selection;
        
        processAttributes(selection);
    }
    
    public void selectValues(){
        System.out.print("-9. Delete Attribute\n");
        System.out.print("-1. Back\n\n");
        
        System.out.print("Select attributes: ");
        
        Scanner in = new Scanner(System.in);
        selection = in.nextInt();
        
        processValues(selection);
    }
    
    public void processAttributes(int inp){
        switch(inp){
            case -1:
                break;
            default:
                if(instances.attribute(inp).isNominal()){
                    showValuesNominal(instances.attribute(inp), instances.attributeStats(inp));
                }else if(instances.attribute(inp).isNumeric()){
                    showValuesNumeric(instances.attribute(inp), instances.attributeStats(inp));
                }
                selectValues();
                
                break;
        }
    }
    
    public void processValues(int inp){
        switch(inp){
            case -1:
                showAttributes();
                selectAttributes();
                break;
            case -9:
                System.out.print("IN\n");
                deleteAttributes(selectedAttr);
                break;
            default:
                break;
        }
    }
    
    public void showAttributes(){
        System.out.println("Attributes from "+instances.relationName()+": ");
        for(int i=0; i<instances.numAttributes(); i++){
            System.out.print(Integer.toString(i)+"-"+instances.attribute(i).name()+"\n");
        }
        System.out.print("\n");
    }
    
    public void showAttributes(Instances instances){
        System.out.println("Attributes from "+instances.relationName()+": ");
        for(int i=0; i<instances.numAttributes(); i++){
            System.out.print(Integer.toString(i)+"-"+instances.attribute(i).name()+"\n");
        }
        System.out.print("\n");
    }
    
    public void showValuesNominal(Attribute attr, AttributeStats attrStats){
        System.out.println("Values from "+attr.name()+": ");
        System.out.println("Type: Nominal");
        System.out.println(attrStats.toString());
        for(int i=0; i<attr.numValues(); i++){
            System.out.print(i+"-"+attr.value(i)+"\n");
        }
        System.out.print("\n");
        
    }
    
    public void showValuesNumeric(Attribute attr, AttributeStats attrStats){
        System.out.println("Values from "+attr.name()+": ");
        System.out.println("Type: Numeric");
        System.out.println(attrStats.toString());
    }
}
