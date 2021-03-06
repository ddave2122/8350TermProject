package DEVSGlycolysis.coupled;

import DEVSGlycolysis.atomic.CoSubstrate;
import DEVSGlycolysis.atomic.ProductReusable;
import simView.ViewableAtomic;
import simView.ViewableComponent;
import simView.ViewableDigraph;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: nick
 * Date: 4/17/14
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Substrate extends ViewableDigraph {

    public static final String inPort1 = "in1";
    public static final String inPort2 = "in2";
    public static final String outPort = "out1";
    public Substrate() {
        this("Substrate");
    }

    public Substrate(String nm) {
        super(nm);
        DNSubstrateConstructor();
    }

    public void DNSubstrateConstructor() {
        this.addOutport(outPort);
        this.addInport(inPort1);
        this.addInport(inPort2);

        ViewableAtomic dNCoSubstrate = new CoSubstrate("CoSubstrate");

        add(dNCoSubstrate);

        addCoupling(this, inPort1, dNCoSubstrate, CoSubstrate.inPort1);
        addCoupling(this, inPort2, dNCoSubstrate, CoSubstrate.inPort2);
        addCoupling(dNCoSubstrate,CoSubstrate.outPort, this, outPort);
    }

    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(515, 284);
        if((ViewableComponent)withName("CoSubstrate")!=null)
             ((ViewableComponent)withName("CoSubstrate")).setPreferredLocation(new Point(115, 116));
    }
}
