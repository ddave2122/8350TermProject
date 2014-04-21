package DEVSGlycolysis.atomic;

import DEVSGlycolysis.entity.SubstratePair;
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
 * Time: 10:58 AM
 *
 * NOTE
 * Selects the necessary enzyme (depending on input)
 */
public class Fixing extends ViewableAtomic {
    private double int_gen_time;
    private rand r;
    private int count;
    protected double arrived, solved;
    protected double observation_time;
    private boolean enzyme, molecule;

    private Product.ProductType pType;
    private AtomicEnzyme.EnzymeType eType;

    private String messageToSendOne, messageToSendTwo;

    public Fixing() {
        this("Glucose", 20);
    }

    public Fixing(String name) {
        this(name, 20);
    }

    public Fixing(String name, double observation_time) {
        super(name);
        addInport("in1");
        addInport("in2");
        addOutport("out1");
        addOutport("out3");

        addNameTestInput("in1", "Substrate");
        addNameTestInput("in2", "enzymeEntry");
        this.observation_time = observation_time;
        enzyme = false;
        molecule = false;
    }

    public void initialize() {
        holdIn("passive", observation_time);
        r = new rand(12345);
        count = 0;
        messageToSendTwo = "Enzyme";
    }

    public void deltext(double e, message x) {
        Continue(e);
        entity val;
        for (int i = 0; i < x.size(); i++) {
            if (messageOnPort(x, "in1", i)) {
                val = x.getValOnPort("in1", i);
                if (val.getName().compareTo("Substrate") == 0) {
                    molecule = true;
                    arrived = this.getSimulationTime();
                    System.out.println(val.getName() + " arrived at time:" + arrived);

                    holdIn("active", 5);
                    messageToSendOne = "";
                    messageToSendTwo = "Enzyme";
                    
                }
                //numOfarrivingcars++;
            }
            if (messageOnPort(x, "in2", i)) {
                val = x.getValOnPort("in2", i);
                if (val.getName().compareTo("enzymeEntry") == 0) {
                    enzyme = true;
                    solved = this.getSimulationTime();
                    if (molecule)
                    {
                        messageToSendOne = "startProcess";
                        messageToSendTwo = "Enzyme";

                        holdIn("active", 5);
                    }
                    else
                        holdIn("Waiting", 10);
                    System.out.println(val.getName() + " is finished at time:" + solved);
                }
                //numOfFinishedCars++;
            }
        }
    }

    @Override
    public void deltint() {
        if (phaseIs("active")) {
            messageToSendOne = "startProcess";
            messageToSendTwo = "Enzyme";

            enzyme = false;
            molecule = false;
            out();
            passivate();
        } else if (phaseIs("passive")) {
            System.out.println("the total service time is: " + (solved - arrived));
            passivate();
        }
        else if (phaseIs("waiting")) {
            System.out.println("the total service time is: " + (solved - arrived));
            out();
            passivate();
        }

    }


    public message out() {
        //System.out.println(name+" out count "+count);
        message m = new message();

          SubstratePair pair = new SubstratePair();
        content con = makeContent("out1", new InputEntity(messageToSendOne, 1));
//      content con2 = makeContent("out3", new InputEntity(messageToSendTwo, 1));
        content con2 = makeContent("out3", new InputEntity("Test 1", 1));
        m.add(con);
        m.add(con2);

        return m;
    }
    
//    public message out() {
//        SubstratePair pair = new SubstratePair();
//        
//        message m = new message();
//        content con = makeContent("out3", new InputEntity("Test 3", 1));
//        content con2 = makeContent("out1", new InputEntity("Test 1", 1));
//
//        m.add(con);
//        m.add(con2);
//
//        return m;
//    }

    private String getMessageOnPortZero(message x) {
        return x.getValOnPort("in1", 0).toString();
    }

    private String getMessageOnPortOne(message x) {
        return x.getValOnPort("in2", 0).toString();
    }

}
