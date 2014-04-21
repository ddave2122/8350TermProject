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
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class AtomicEnzyme extends ViewableAtomic {
    public enum EnzymeType {
        Hexokinase, Phosphoglucose_isomerase, Phosphofructokinase, Triosephosphateisomerase,
        Glyceraldehydephosphatedehyrdogenase, Phosphoglyceratekinase, Phosphoglyceratemutase,
        Pyruvatekinase, Aldolase, Phosphopyruvatehydratase;
    }

    private double int_gen_time;
    private rand r;
    private int count;

    private String messageToSend;
    private SubstrateEnzymeTriple triple;

    public AtomicEnzyme() {
        this("AtomicEnzyme");
    }

    public AtomicEnzyme(String name) {
        super(name);
        addInport("in1");
        addOutport("out1");
        addNameTestInput("in1", "AtomicEnzyme");

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
            this.triple = (SubstrateEnzymeTriple) x.getValOnPort("in1", 0);
            holdIn("active", 2);
        }
    }

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
        content con = makeContent("out1", this.triple);
        m.add(con);

        return m;
    }

    private String getMessageOnPortZero(message x) {
        return x.getValOnPort("in1", 0).toString();
    }
}
