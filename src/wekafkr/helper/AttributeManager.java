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
    
    public AttributeManager(Instances instances){
        this.instances = instances;
    }
    
     public void showAttributes(){
        System.out.println("Attributes from " + this.instances.relationName() + ": ");
        for(int i=0; i < this.instances.numAttributes(); i++){
            System.out.print(Integer.toString(i) + "-" + this.instances.attribute(i).name() + "\n");
        }
        
        selectAttributes();
    }
    
    public void deleteAttributes(int input){
        this.instances.deleteAttributeAt(input);
        showAttributes();
    }
    
    public void selectAttributes(){
        System.out.print("\n-1 Cancel\n");
        System.out.print("Select attributes: ");
        
        Scanner in = new Scanner(System.in);
        selection = in.nextInt();
        selectedAttr = selection;
        
        processAttributes(selection);
    }
    
    public void processAttributes(int input){
        switch(input){
            case -1:
                break;
            default:
                if(this.instances.attribute(input).isNominal()) {
                    showValuesNominal(this.instances.attribute(input), this.instances.attributeStats(input));
                }
                else if(this.instances.attribute(input).isNumeric()) {
                    showValuesNumeric(this.instances.attribute(input), this.instances.attributeStats(input));
                }
                
                selectValues();
                
                break;
        }
    }
    
    public void selectValues(){
        System.out.print("-9. Delete Attribute\n");
        System.out.print("-1. Cancel\n");
        System.out.print("Action: ");
        
        Scanner in = new Scanner(System.in);
        selection = in.nextInt();
        
        processValues(selection);
    }
    
    public void processValues(int inp){
        switch(inp){
            case -1:
                break;
            case -9:
                deleteAttributes(selectedAttr);
                break;
            default:
                break;
        }
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
