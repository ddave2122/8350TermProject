package DEVSGlycolysis.coupled;

import DEVSGlycolysis.atomic.*;
import simView.ViewableAtomic;
import simView.ViewableComponent;
import simView.ViewableDigraph;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: nick
 * Date: 4/17/14
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class Enzyme extends ViewableDigraph {

    public Enzyme() {
        this("Enzyme");
    }

    public Enzyme(String nm) {
        super(nm);
        DNEnzymeConstructor();
    }

    public void DNEnzymeConstructor() {
        this.addOutport("out1");
        this.addInport("in11");

        ViewableAtomic dNChoice = new DEVSGlycolysis.atomic.Choice("Choice", 7);
        //ViewableAtomic dNAMEnzyme = new AtomicEnzyme("AtomicEnzyme");
        ViewableAtomic dNAldolase = new Aldolase("Aldolase");
        ViewableAtomic dNActiva = new Activation("Activation");

        add(dNChoice);
        //add(dNAMEnzyme);
        add(dNAldolase);
        add(dNActiva);

        addCoupling(this, "in11", dNChoice, "in1");
        //addCoupling(dNChoice, "out1", dNAMEnzyme, "in1");
        addCoupling(dNChoice, "out1", dNActiva, "in1");
        addCoupling(dNChoice, "out2", dNAldolase, "in1");
        //addCoupling(dNAMEnzyme, "out1", dNActiva, "in1");
        addCoupling(dNAldolase, "out1", dNActiva, "in2");
        addCoupling(dNActiva, "out1", this, "out1");
    }

    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(625, 276);
        if((ViewableComponent)withName("Choice")!=null)
             ((ViewableComponent)withName("Choice")).setPreferredLocation(new Point(20, 100));
        if((ViewableComponent)withName("Aldolase")!=null)
             ((ViewableComponent)withName("Aldolase")).setPreferredLocation(new Point(172, 186));
        if((ViewableComponent)withName("Activation")!=null)
             ((ViewableComponent)withName("Activation")).setPreferredLocation(new Point(337, 100));
    }
}
