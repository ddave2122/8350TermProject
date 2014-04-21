package DEVSGlycolysis.atomic;

import DEVSGlycolysis.entity.SubstrateEnzymeTriple;
import DEVSGlycolysis.entity.SubstratePair;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 * Created with IntelliJ IDEA.
 * User: nick
 * Date: 4/17/14
 * Time: 10:58 AM
 *
 * NOTE
 * Selects the necessary enzyme (depending on input)
 */
public class Fixing extends ViewableAtomic {
    private double int_gen_time;
    private rand r;
    private int count;
    protected double arrived, solved;
    protected double observation_time;


    private SubstratePair pair;
    private SubstrateEnzymeTriple triple;

    public static final String inPort1 = "in1";
    public static final String inPort2 = "in2";
    public static final String outPort1 = "out1";
    public static final String outPort2 = "out2";

    public Fixing() {
        this("Glucose", 20);
    }

    public Fixing(String name) {
        this(name, 20);
    }

    public Fixing(String name, double observation_time) {
        super(name);
        addInport(inPort1);
        addInport(inPort2);
        addOutport(outPort1);
        addOutport(outPort2);

        addNameTestInput(inPort1, "Substrate");
        addNameTestInput(inPort2, "enzymeEntry");
        this.observation_time = observation_time;
    }

    public void initialize() {
        holdIn("passive", observation_time);
        r = new rand(12345);
        count = 0;
        passivate();
    }

    public void deltext(double e, message x) {
        Continue(e);

        for (int i = 0; i < x.size(); i++) {
            if (messageOnPort(x, inPort1, i)) {
                this.pair = (SubstratePair)x.getValOnPort(inPort1, i);
                this.triple = null;
                arrived = this.getSimulationTime();
                holdIn("waiting", 5);
            }
            if (messageOnPort(x, inPort2, i)) {
                this.triple = (SubstrateEnzymeTriple)x.getValOnPort(inPort2, i);
                solved = this.getSimulationTime();
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
            System.out.println("the total service time is: " + (solved - arrived));
            passivate();
        }
        else if (phaseIs("waiting")) {
            System.out.println("the total service time is: " + (solved - arrived));
            out();
            holdIn("WaitingM", 1000);
        }

    }


    public message out() {
        message m = new message();
        content con;

        if (this.triple != null) {
            con = makeContent(outPort1, this.triple);
        } else {
            con = makeContent(outPort2, this.pair);
        }

        m.add(con);
        return m;
    }

}
