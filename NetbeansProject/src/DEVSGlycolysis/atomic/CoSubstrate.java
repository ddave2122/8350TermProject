package DEVSGlycolysis.atomic;

import DEVSJAVALab.InputEntity;
import GenCol.entity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 * Created with IntelliJ IDEA.
 * User: nick
 * Date: 4/17/14
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class CoSubstrate extends ViewableAtomic {
    public enum CoSubstrateType {ATP, NADPlus, ADP, None};

    private double int_gen_time;
    private rand r;
    private int count;

    private String messageToSend;

    public CoSubstrate() {
        this("Screen");
    }

    public CoSubstrate(String name) {
        super(name);
        addInport("in2");
        addInport("in1");
        addOutport("out1");

        //int_gen_time = period ;
    }

    public void initialize() {
        holdIn("passive", int_gen_time);
        r = new rand(12345);
        count = 0;
    }

    public void deltext(double e, message x) {
        Continue(e);

        if (messageOnPort(x, "in1", 0)) {
            entity product = x.getValOnPort("in1", 0);
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
        return x.getValOnPort("in1", 0).toString();
    }

}