/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr.helper;

import java.util.Scanner;
import weka.core.Attribute;
import weka.core.Instances;

/**
 *
 * @author fauzanrifqy
 */
public class AttributeManager {
    
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
            default:
                showValues(instances.attribute(inp));
                selectValues();
                break;
        }
    }
    
    public void processValues(int inp){
        switch(inp){
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
    
    public void showValues(Attribute attr){
        System.out.println("Values from "+attr.name()+": ");
        for(int i=0; i<attr.numValues(); i++){
            System.out.print(i+"-"+attr.value(i)+"\n");
        }
        System.out.print("\n");
    }
}
