/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DEVSGlycolysis.atomic;

import DEVSGlycolysis.entity.ReactionEntity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 * @author David
 */
public class Product extends ViewableAtomic {
    public enum ProductType {
        Glucose, Glucose6Phosphate, Fructose6Phosphate,
        Fructose1_6BiPhosphate, GlycerAlderhyde3Phosphate, DiHydroxideAcetonePhosphate,
        _1_3BiPhosphoGlycerate, _3PhosphoGlycerate, _2PhosphoGlycerate, PhosphoenolPyruvate, Pyruvate
    }

    private double int_gen_time;
    private rand r;
    private int count;
    private ReactionEntity reaction;

    public static final String inPort = "in1";
    public static final String outPort1 = "out1";
    public static final String outPort2 = "out2";

    public Product() {
        this("Product", 11);
    }
    public Product(String name)
    {
        this("Product", 11);
    }

    public Product(String name, double period) {
        super(name);
        addInport(inPort);
        addOutport(outPort1);
        addOutport(outPort2);
        
        addNameTestInput(inPort, "Product");
        
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

        if (messageOnPort(x, inPort, 0)) {
            this.reaction = (ReactionEntity)x.getValOnPort(inPort, 0);
            holdIn("active", 3);  //Hold in active for 5 seconds
        }
    }

    @Override
    public void deltint() {
        if (phaseIs("active")) {
            out();
            passivate();
        }  
        else
            System.out.println("UNKNOWN PHASE: " + getPhase());
    }

    @Override
    public message out() {
        message m = new message();
        content con = null;
        switch (this.reaction.getProduct()) {
            case Pyruvate:
                // if Pyruvate we are done
                con = makeContent(outPort2, this.reaction);
                double time = this.reaction.getReactionTime();
                System.out.print("Total reaction time:");
                System.out.println(this.getSimulationTime() - time);
                break;
            default:
                // otherwise recycle product back into network
                con = makeContent(outPort1, this.reaction);
                break;
        }

        m.add(con);
        return m;
    }
}
