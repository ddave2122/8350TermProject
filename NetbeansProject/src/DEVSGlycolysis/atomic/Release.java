package DEVSGlycolysis.atomic;

import DEVSGlycolysis.entity.ReactionEntity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 * Created with IntelliJ IDEA.
 * User: nick
 * Date: 4/17/14
 * Time: 11:02 AM
 *
 * NOTE
 * This forwards the result of the reaction (in transformation)
 */
public class Release extends ViewableAtomic {
    private double int_gen_time;
    private rand r;
    private int count;

    private ReactionEntity reaction;

    private static final String inPort = "in1";
    private static final String outPort = "out1";

    public Release() {
        this("Glucose");
    }

    public Release(String name) {
        super(name);
        addInport(inPort);
        addOutport(outPort);
        addNameTestInput(inPort, "Transformation");
    }

    public void initialize() {
        holdIn("passive", int_gen_time);
        count = 0;
        passivate();
    }

    @Override
    public void deltext(double e, message x) {
        if (messageOnPort(x, "in1", 0)) {
            this.reaction = (ReactionEntity) x.getValOnPort("in1", 0);
            holdIn("active", this.reaction.getReactionTime());
        }
        else
            System.out.println("UNKNOWN MESSAGE: " + getMessageOnPortZero(x));
    }

    @Override
    public void deltint() {
        if (phaseIs("active")) {
            out();
            passivate();
        } else
            System.out.println("UNKNOWN PHASE: " + getPhase());
    }
    
    @Override
    public message out() {
        message m = new message();
        content con = makeContent("out1", this.reaction);
        m.add(con);

        return m;
    }
    
        private String getMessageOnPortZero(message x) {
        return x.getValOnPort("in1", 0).toString();
    }

}
