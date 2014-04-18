package DEVSJAVALab;

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
public class DNAMEnzyme extends ViewableAtomic {

    private double int_gen_time;
    private rand r;
    private int count;

    private String messageToSend;

    public DNAMEnzyme() {
        this("DNAMEnzyme");
    }

    public DNAMEnzyme(String name) {
        super(name);
        addInport("in1");
        addOutport("out1");
    }

    public void initialize() {
        holdIn("passive", int_gen_time);
        r = new rand(12345);
        count = 0;
    }

    public void deltext(double e, message x) {
        Continue(e);

        if (messageOnPort(x, "in", 0)) {
            if (getMessageOnPortZero(x).equals("movement"))
                holdIn("passive", 300);  //Hold in active for 5 minutes
            else if (getMessageOnPortZero(x).equals("active"))
                holdIn("active", 1800);  //Hold in active for 30 minutes
            else if (getMessageOnPortZero(x).equals("hibernate"))
                holdIn("hibernate", Integer.MAX_VALUE);
            else
                System.out.println("UNKNOWN MESSAGE: " + getMessageOnPortZero(x));
        }
    }

    public void deltint() {
        if (phaseIs("passive")) {
            messageToSend = "on";
            out();
        } else if (phaseIs("active")) {
            messageToSend = "sleep";
            out();
        } else if (phaseIs("hibernate")) {
            messageToSend = "hibernate";
            out();
        } else
            System.out.println("UNKNOWN PHASE: " + getPhase());
    }


    public message out() {
        //System.out.println(name+" out count "+count);
        message m = new message();
        //content con = makeContent("out", new entity("car" + count));
        content con = makeContent("out", new InputEntity(messageToSend, 1));
        m.add(con);

        return m;
    }

    private String getMessageOnPortZero(message x) {
        return x.getValOnPort("in", 0).toString();
    }
}
