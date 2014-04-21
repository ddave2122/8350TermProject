package DEVSGlycolysis.atomic;

import DEVSGlycolysis.entity.ReactionEntity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 * Created with IntelliJ IDEA. User: nick Date: 4/17/14 Time: 11:05 AM To change
 * this template use File | Settings | File Templates.
 */
public class GlucoseGenerator extends ViewableAtomic {

    protected double int_gen_time;
    protected int count;
    protected rand r;

    public static final String outPort = "out1";

    public GlucoseGenerator() {
        this("GlucoseGenerator", 3);
    }

    public GlucoseGenerator(String name, double period) {
        super(name);
        addOutport(outPort);
        int_gen_time = period;
    }

    @Override
    public void initialize() {
        holdIn("active", int_gen_time);
    }

    @Override
    public void deltint() {
        passivate();
    }

    @Override
    public message out() {
        message m = new message();
        content con = makeContent(outPort, new ReactionEntity());
        m.add(con);

        return m;
    }
}
