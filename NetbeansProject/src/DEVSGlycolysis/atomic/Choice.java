package DEVSGlycolysis.atomic;

import DEVSJAVALab.InputEntity;
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
public class Choice extends ViewableAtomic {
    private double int_gen_time;
    private rand r;
    private int count;

    private String messageToSendOne, messageToSendTwo;

    public Choice() {
        this("Choice", 4);
    }

    public Choice(String name, double period) {
        super(name);
        addInport("in1");
        addOutport("out1");
        addOutport("out2");
        addNameTestInput("in1", "Reaction");

        int_gen_time = period ;
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

        if (messageOnPort(x, "in1", 0)) {
            if (getMessageOnPortZero(x).equals("Reaction"))
            {
                holdIn("active", 5);  //Hold in active for 5 seconds
                messageToSendOne = "AtomicEnzyme";
                messageToSendTwo = "Aldolase";
            }
            else
                System.out.println("UNKNOWN MESSAGE: " + getMessageOnPortZero(x));
        }
    }

    @Override
    public void deltint() {
        if (phaseIs("passive")) {
             //Do nothing
        } 
        else if (phaseIs("active")) {
            messageToSendOne = "AtomicEnzyme";
            messageToSendTwo = "Aldolase";
            out();
            passivate();
        }  
        else
            System.out.println("UNKNOWN PHASE: " + getPhase());
    }

    @Override
    public message out() {
        message m = new message();
        content con = makeContent("out1", new InputEntity(messageToSendOne, 1));
        content con2 = makeContent("out2", new InputEntity(messageToSendTwo, 1));
        m.add(con);
        m.add(con2);

        return m;
    }

    private String getMessageOnPortZero(message x) {
        return x.getValOnPort("in1", 0).toString();
    }
}
