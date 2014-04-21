package DEVSGlycolysis.atomic;

import DEVSGlycolysis.entity.SubstrateEnzymeTriple;
import DEVSJAVALab.InputEntity;
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
public class Aldolase extends ViewableAtomic {
    private double int_gen_time;
    private rand r;
    private int count;

    private SubstrateEnzymeTriple triple;
    private String messageToSend;


    public static final String inPort = "in1";
    public static final String outPort = "out1";

    public Aldolase() {
        this("Aldolase");
    }

    public Aldolase(String name) {
        super(name);
        addInport(inPort);
        addOutport(outPort);
        addNameTestInput("in1", "Aldolase");
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
            this.triple = (SubstrateEnzymeTriple) x.getValOnPort(inPort, 0);
            holdIn("active", 1);  //Hold in active for 5 seconds

        } else {
                System.out.println("UNKNOWN MESSAGE: " + getMessageOnPortZero(x));
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
        SubstrateEnzymeTriple outMsg = new SubstrateEnzymeTriple("Triple", Product.ProductType.DiHydroxideAcetonePhosphate,
                CoSubstrate.CoSubstrateType.None, AtomicEnzyme.EnzymeType.Triosephosphateisomerase);
        content con = makeContent(outPort, outMsg);
        m.add(con);

        return m;
    }

    private String getMessageOnPortZero(message x) {
        return x.getValOnPort(inPort, 0).toString();
    }
}
