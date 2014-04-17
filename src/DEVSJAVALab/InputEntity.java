/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DEVSJAVALab;

import GenCol.entity;

/**
 * @author David
 */
public class InputEntity extends entity {
    protected double processingTime;
    protected double price;
    protected int type;

    public InputEntity() {
        this("movement", 1);
    }

    public InputEntity(String name, int type) {
        super(name);
        this.type = type;
    }

    public double getProcessingTime() {
        return processingTime;
    }

    public double getType() {
        return type;
    }

    public String toString() {
        //return name+"_"+processingTime;
        return name;
        //return name+"_"+((double)((int)(processingTime*100)))/100;
    }

}