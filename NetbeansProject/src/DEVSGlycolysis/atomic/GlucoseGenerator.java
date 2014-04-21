package DEVSGlycolysis.atomic;

import DEVSGlycolysis.GlucoseEntity;
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

    public GlucoseGenerator() {
        this("GlucoseGenerator", 7);
    }

    public GlucoseGenerator(String name, double period) {
        super(name);
        addOutport("out1");

        int_gen_time = period;
    }

    @Override
    public void initialize() {
        holdIn("active", int_gen_time);
        r = new rand(12345);
        count = 0;
    }

    @Override
    public void deltext(double e, message x) {
        Continue(e);
        for (int i = 0; i < x.getLength(); i++) {
            if (messageOnPort(x, "in", i)) { //the stop message from tranducer
                passivate();
            }
        }
    }

    @Override
    public void deltint() {

        if (phaseIs("active")) {
            count = count + 1;
//   holdIn("active",int_gen_time);
            holdIn("active", 1000 );
        } else {
            passivate();
        }
    }

    @Override
    public message out() {
//System.out.println(name+" out count "+count);
        message m = new message();
//   content con = makeContent("out", new entity("car" + count));
        content con = makeContent("out1", new GlucoseEntity("Glucose", 5 + r.uniform(20), 50 + r.uniform(100), 1));
        m.add(con);

        return m;
    }
}
