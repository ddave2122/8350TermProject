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
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class Activation extends ViewableAtomic {
    private double int_gen_time;
    private rand r;
    private int count;
    private boolean AtomicEnzyme, Aldolase;
    private String messageToSend;

    public Activation() {
        this("Activation");
    }

    public Activation(String name) {
        super(name);
        addInport("in1");
        addInport("in2");
        addOutport("out1");

        //int_gen_time = period ;
    }

    @Override
    public void initialize() {
        holdIn("passive", int_gen_time);
        r = new rand(12345);
        count = 0;
        passivate();
    }

    @Override
    public void deltext(double e, message x) {
        Continue(e);
        
        entity val;
        for (int i = 0; i < x.size(); i++) {
            if (messageOnPort(x, "in1", i)) {
                val = x.getValOnPort("in1", i);
                if (val.getName().compareTo("AtomicEnzyme") == 0) {
                    AtomicEnzyme = true;
                    if (Aldolase)
                    {
                        holdIn("active", 5);
                        messageToSend = "Activation";
                    }
                    else
                        holdIn("Waiting", 10);
                }
                //numOfarrivingcars++;
            }
            if (messageOnPort(x, "in2", i)) {
                val = x.getValOnPort("in2", i);
                if (val.getName().compareTo("Aldolase") == 0) {
                    Aldolase = true;
                    if (AtomicEnzyme)
                    {
                        holdIn("active", 5);
                        messageToSend = "Activation";
                    }
                    else
                        holdIn("Waiting", 10);
                }
                //numOfFinishedCars++;
            }
        }
    }

    @Override
    public void deltint() {
         if (phaseIs("active")) {
            messageToSend = "Activation";
            Aldolase = false;
            AtomicEnzyme = false;
            out();
            passivate();
        } else if (phaseIs("passive")) {
            passivate();
        }
    }


    public message out() {
        message m = new message();
        content con = makeContent("out1", new InputEntity(messageToSend, 1));
        m.add(con);

        return m;
    }
}
