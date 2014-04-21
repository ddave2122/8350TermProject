package DEVSGlycolysis.atomic;

import DEVSGlycolysis.entity.SubstrateEnzymeTriple;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 * Created with IntelliJ IDEA.
 * User: nick
 * Date: 4/17/14
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class Activation extends ViewableAtomic {
    private double int_gen_time;
    private rand r;
    private int count;

    private SubstrateEnzymeTriple triple;
    private SubstrateEnzymeTriple aldTriple;

    public Activation() {
        this("Activation");
    }

    public Activation(String name) {
        super(name);
        addInport("in1");
        addInport("in2");
        addOutport("out1");
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
        for (int i = 0; i < x.size(); i++) {
            if (messageOnPort(x, "in1", i)) {
                this.triple = (SubstrateEnzymeTriple) x.getValOnPort("in1", i);
                if (this.triple.getEnzymeType() == AtomicEnzyme.EnzymeType.Aldolase && this.aldTriple == null) {
                    holdIn("waiting", 100);
                } else {
                    holdIn("active", 1);
                }
            }
            if (messageOnPort(x, "in2", i)) {
                this.aldTriple = (SubstrateEnzymeTriple) x.getValOnPort("in2", i);
                holdIn("active", 1);
            }
        }
    }

    @Override
    public void deltint() {
         if (phaseIs("active")) {
            out();
            passivate();
        } else if (phaseIs("passive")) {
            passivate();
        }
    }


    public message out() {
        message m = new message();
        content con = makeContent("out1", this.triple);
        this.aldTriple = null;
        m.add(con);

        return m;
    }
}
