/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DEVSJAVALab;

import GenCol.entity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 *
 * @author David
 */
public class KeyboardMouse extends ViewableAtomic{

    protected double int_gen_time;
  protected int count;
  protected rand r;
  entity movement;
  private String messageToSend;

  public KeyboardMouse() {this("Screen");}

    public KeyboardMouse(String name){
       super(name);
       addInport("in");
       addOutport("out");
       addNameTestInput("in", "movement");
       addNameTestInput("in", "sleep");
       addNameTestInput("in", "hibernate");
       //int_gen_time = period ;
    }

    public void initialize(){
       // int_gen_time = 5;
       holdIn("active", int_gen_time);
       r = new rand(12345);
       count = 0;
    }

    public void  deltext(double e,message x)
    {
        Continue(e);

    if(messageOnPort(x, "in", 0))
    {
       if(getMessageOnPortZero(x).equals("sleep"))
           holdIn("active", 2);
       else if(getMessageOnPortZero(x).equals("movement"))
           holdIn("movement", 3);
       else if(getMessageOnPortZero(x).equals("hibernate"))
           holdIn("hibernate", 18);
       else
            System.out.println("UNKNOWN PHASE IN keyboardMouse: " + getMessageOnPortZero(x));
    }
    }

public void  deltint( )
{

        if(phaseIs("active"))
        {
            messageToSend = "movement";
            out();
        }
        else if(phaseIs("sleep"))
        {
            messageToSend = "sleep";
            out();
        }
        else if(phaseIs("hibernate"))
        {
            messageToSend = "hibernate";
            out();
        }
        else 
            System.out.println("UNKNOWN PHASE IN KeyboardMouse: " + getPhase());
        //else passivate();
    }

   
    public message out()
    {
       //System.out.println(name+" out count "+count);
       message  m = new message();
       //content con = makeContent("out", new entity("car" + count));
       content con = makeContent("out", new InputEntity(messageToSend, 1));
       m.add(con);

       return m;
    }
    
    private String getMessageOnPortZero(message x)
    {
        return x.getValOnPort("in", 0).toString();
    }
}

