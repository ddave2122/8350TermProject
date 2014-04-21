/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DEVSGlycolysis.atomic;

import DEVSJAVALab.InputEntity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 * @author David
 */
public class Product extends ViewableAtomic {
    public enum ProductType {
        Glucose, Glucose6Pphosphate, Fructose6Phosphate,
        Fructose1_6BiPhosphate, GlycerAlderhyde3Phosphate, DiHydroxideAcetonePhosphate,
        _1_3BiPhosphoGlycerate, _3PhosphoGlycerate, _2PhosphoGlycerate, PhosphoenolPyruvate, Pyruvate
    }

    private double int_gen_time;
    private rand r;
    private int count;
    private String messageToSendOne, messageToSendTwo;

    public Product() {
        this("Product", 11);
    }
    public Product(String name)
    {
        this("Product", 11);
    }

    public Product(String name, double period) {
        super(name);
        addInport("in1");
        addOutport("out1");
        addOutport("out2");
        
        addNameTestInput("in1", "Product");
        
        int_gen_time = period ;
    }

    @Override
    public void initialize() {
        holdIn("passive", int_gen_time);
        r = new rand(12345);
        count = 0;
        passivate();
    }

    @Override
    public void deltext(double e, message x) {
        Continue(e);

        if (messageOnPort(x, "in1", 0)) {
            if (getMessageOnPortZero(x).equals("Product"))
            {
                holdIn("active", 5);  //Hold in active for 5 seconds
                messageToSendOne = "AtomicEnzyme";
                messageToSendTwo = "Aldolase";
            }
            else
                System.out.println("UNKNOWN MESSAGE: " + getMessageOnPortZero(x));
        }
    }

    @Override
    public void deltint() {
         if (phaseIs("passive")) {
             //Do nothing
        } 
        else if (phaseIs("active")) {
            messageToSendOne = "Glucose-6";
            messageToSendTwo = "Pyruvate";
            out();
            passivate();
        }  
        else
            System.out.println("UNKNOWN PHASE: " + getPhase());
    }

    @Override
    public message out() {
 message m = new message();
        content con = makeContent("out1", new InputEntity(messageToSendOne, 1));
        content con2 = makeContent("out2", new InputEntity(messageToSendTwo, 1));
        m.add(con);
        m.add(con2);

        return m;
    }

    private String getMessageOnPortZero(message x) {
        return x.getValOnPort("in1", 0).toString();
    }
}
