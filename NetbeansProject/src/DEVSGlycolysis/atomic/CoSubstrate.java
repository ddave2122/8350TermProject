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
    private boolean glucose6, hexokinase;

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

    @Override
    public void initialize() {
        holdIn("passive", 11);
        r = new rand(12345);
        count = 0;
        passivate();
    }
    
    @Override
    public void deltext(double e, message x) {
        Continue(e);
        entity val;
        for (int i = 0; i < x.size(); i++) {
            if (messageOnPort(x, "in2", i)) {
                val = x.getValOnPort("in2", i);
                if (val.getName().compareTo("Hexokinase") == 0) {
                    hexokinase = true;
                    if (glucose6)
                    {
                        holdIn("active", 5);
                        messageToSend = "Substrate";
                    }
                    else
                        holdIn("Waiting", Integer.MAX_VALUE);
                }
            }
            if (messageOnPort(x, "in1", i)) {
                val = x.getValOnPort("in1", i);
                if (val.getName().compareTo("Glucose-6") == 0) {
                    glucose6 = true;
                    if (hexokinase) 
                    {
                        holdIn("active", 5);
                        messageToSend = "Substrate";
                    }
                    else
                        holdIn("Waiting", Integer.MAX_VALUE);
                }
            }
        }
    }
    @Override
    public void deltint() {
        if (phaseIs("active")) {
            messageToSend = "Substrate";
            glucose6 = false;
            hexokinase = false;
            out();
            passivate();
        } else if (phaseIs("passive")) {
            passivate();
        } else
            System.out.println("UNKNOWN PHASE: " + getPhase());
    }

    @Override
    public message out() {
        message m = new message();
        content con = makeContent("out1", new InputEntity(messageToSend, 1));
        m.add(con);

        return m;
    }

    private String getMessageOnPort(message x, String port) {
        return x.getValOnPort(port, 0).toString();
    }

}