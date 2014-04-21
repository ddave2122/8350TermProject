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
 * Time: 10:54 AM
 *
 * NOTE
 * This class outputs reusable products in the network
 */
public class ProductReusable extends ViewableAtomic {

    private double int_gen_time;
    private rand r;
    private int count;

    private ReactionEntity reaction;

    public static final String inPort = "in0";
    public static final String outPort = "out1";

    public ProductReusable() {
        this("ProductReusable", 7);
    }

    public ProductReusable(String name, double period) {
        super(name);
        addInport(inPort);
        addOutport(outPort);
        
        addNameTestInput(inPort, "Glucose");

        int_gen_time = period;
    }


    public void initialize() {
        holdIn("passive", int_gen_time);
        r = new rand(12345);
        count = 0;
        passivate();
    }

    public void deltext(double e, message x) {
        Continue(e);

        if (messageOnPort(x, inPort, 0)) {
            this.reaction = (ReactionEntity) x.getValOnPort(inPort, 0);
            holdIn("active", 1);
        }
        else
            System.out.println("UNKNOWN MESSAGE: " + getMessageOnPortZero(x));

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
        content con = makeContent(outPort, this.reaction);
        m.add(con);

        return m;
    }

    private String getMessageOnPortZero(message x) {
        return x.getValOnPort(inPort, 0).toString();
    }

}
