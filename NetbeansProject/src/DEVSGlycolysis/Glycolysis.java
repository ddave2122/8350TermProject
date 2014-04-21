/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DEVSGlycolysis;

import DEVSGlycolysis.atomic.GlucoseGenerator;
import DEVSGlycolysis.atomic.Product;
import DEVSGlycolysis.coupled.Enzyme;
import DEVSGlycolysis.coupled.Reaction;
import DEVSGlycolysis.coupled.Substrate;
import simView.ViewableAtomic;
import simView.ViewableComponent;
import simView.ViewableDigraph;

import java.awt.*;

/**
 * @author David
 */
public class Glycolysis extends ViewableDigraph {

    public Glycolysis() {
        this("Glycolysis");
    }

    public Glycolysis(String nm) {
        super(nm);
        GlycolysisConstructor();
    }

    public void GlycolysisConstructor() {
        this.addOutport("out1");

        ViewableDigraph dNReaction = new Reaction("Reaction");
        ViewableDigraph dNSubstrate = new Substrate("Substrate");
        ViewableDigraph dNEnzyme = new Enzyme("Enzyme");
        ViewableAtomic dNProduct = new Product("Product");
        ViewableAtomic dNGlucoseGenerator = new GlucoseGenerator();


        add(dNReaction);
        add(dNSubstrate);
        add(dNEnzyme);
        add(dNProduct);
        add(dNGlucoseGenerator);

        addCoupling(dNGlucoseGenerator, "out1", dNSubstrate, "in2");
        addCoupling(dNSubstrate, "out1", dNReaction, "in1");
        addCoupling(dNEnzyme, "out1", dNReaction, "in2");
        addCoupling(dNReaction, "out1", dNProduct, "in1");
        addCoupling(dNProduct, "out1", dNSubstrate, "in1");
        addCoupling(dNProduct, "out2", this, "out1");
        addCoupling(dNReaction, "out2", dNEnzyme, "in11");

    }

    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(1502, 786);
        if((ViewableComponent)withName("Reaction")!=null)
             ((ViewableComponent)withName("Reaction")).setPreferredLocation(new Point(601, 35));
        if((ViewableComponent)withName("Substrate")!=null)
             ((ViewableComponent)withName("Substrate")).setPreferredLocation(new Point(185, 24));
        if((ViewableComponent)withName("GlucoseGenerator")!=null)
             ((ViewableComponent)withName("GlucoseGenerator")).setPreferredLocation(new Point(13, 74));
        if((ViewableComponent)withName("Product")!=null)
             ((ViewableComponent)withName("Product")).setPreferredLocation(new Point(938, 461));
        if((ViewableComponent)withName("Enzyme")!=null)
             ((ViewableComponent)withName("Enzyme")).setPreferredLocation(new Point(160, 318));
    }
}
