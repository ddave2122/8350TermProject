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
 * Time: 10:55 AM
 *
 * NOTE
 * The starting glucose molecule in the network
 */
public class Glucose extends ViewableAtomic {

    private double int_gen_time;
    private rand r;
    private int count;

    private String messageToSend;

    public Glucose() {
        this("Glucose");
    }

    public Glucose(String name) {
        super(name);
        addInport("in1");
        addOutport("out1");
        addNameTestInput("in1", "Glucose");
        

        //int_gen_time = period ;
    }

    @Override
    public void initialize() {
        holdIn("passive", 6);
        r = new rand(12345);
        count = 0;
        passivate();
        
    }

    @Override
    public void deltext(double e, message x) {
        Continue(e);

        if (messageOnPort(x, "in1", 0)) {
            if (getMessageOnPortZero(x).equals("Glucose")) {
                holdIn("active", 3);  //Hold in active for 5 minutes
                messageToSend = "Glucose-6";
            }
            else
                System.out.println("UNKNOWN MESSAGE: " + getMessageOnPortZero(x));
        }
    }

    @Override
    public void deltint() {
        if (phaseIs("active")) {
            messageToSend = "Glucose-6";
            out();
            passivate();
        } else
            System.out.println("UNKNOWN PHASE: " + getPhase());
    }

    @Override
    public message out() {
        //System.out.println(name+" out count "+count);
        message m = new message();
        //content con = makeContent("out", new entity("car" + count));
        content con = makeContent("out1", new ReactionEntity());
        m.add(con);

        return m;
    }

    private String getMessageOnPortZero(message x) {
        return x.getValOnPort("in1", 0).toString();
    }

}
