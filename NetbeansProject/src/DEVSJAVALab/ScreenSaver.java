/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DEVSJAVALab;

import DEVSGlycolysis.entity.InputEntity;
import GenCol.entity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 * @author David
 */
public class ScreenSaver extends ViewableAtomic {

    protected double int_gen_time;
    protected int count;
    protected rand r;
    entity movement;
    private String messageToSend;

    public ScreenSaver() {
        this("Screen");
    }

    public ScreenSaver(String name) {
        super(name);
        addInport("in");
        addOutport("out");

        //int_gen_time = period ;
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