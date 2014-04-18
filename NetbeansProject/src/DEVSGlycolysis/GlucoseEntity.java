/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DEVSGlycolysis;

import GenCol.entity;

/**
 * @author David
 */
public class GlucoseEntity extends entity {
    protected double processingTime;
    protected double price;
    protected int priority;

    public GlucoseEntity() {
        this("Glucose", 10, 10, 1);
    }

    public GlucoseEntity(String name, double _procTime, double _price, int _priority) {
        super(name);
        processingTime = _procTime;
        price = _price;
        priority = _priority;
    }

    public double getProcessingTime() {
        return processingTime;
    }

    public double getPrice() {
        return price;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return name + "_" + processingTime;
        //return name+"_"+((double)((int)(processingTime*100)))/100;
    }

}
