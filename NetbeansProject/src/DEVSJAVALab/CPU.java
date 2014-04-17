/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DEVSJAVALab;

import DEVSJAVALab.vehicleEntity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 *
 * @author David
 */
public class CPU extends ViewableAtomic{

  protected double int_gen_time;
  protected int count;
  protected rand r;

  public CPU() {this("CPU", 300);}

    public CPU(String name,double period){
       super(name);
       addInport("in");
       addOutport("out");

       int_gen_time = period ;
    }

    public void initialize(){
       holdIn("active", int_gen_time);
       r = new rand(12345);
       count = 0;
    }

public void deltext(double e,message x)
    {
    Continue(e);
       if(messageOnPort(x, "in", 0))
       {
           if(getMessageOnPortZero(x).equals("active"))
               holdIn("active", Integer.MAX_VALUE);
           else if(getMessageOnPortZero(x).equals("sleep"))
               holdIn("active", Integer.MAX_VALUE);
           else if(getMessageOnPortZero(x).equals("hibernate"))
               holdIn("SLOW", Integer.MAX_VALUE);
       }
    }

    public void  deltint( )
    {
        if(phaseIs("sleep"))
           holdIn("off",Integer.MAX_VALUE);
        else if(phaseIs("hibernate"))
            holdIn("off",Integer.MAX_VALUE);
        else if(phaseIs("active"))
            holdIn("active", Integer.MAX_VALUE);
    }

    
    private String getMessageOnPortZero(message x)
    {
        return x.getValOnPort("in", 0).toString();
    }
}