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


    private String messageToSend;

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
        addNameTestInput("in1", "startMolecule");
        addNameTestInput("in2", "enzymeEntry");
        this.observation_time = observation_time;
        enzyme = false;
        molecule = false;
    }

    public void initialize() {
        holdIn("passive", observation_time);
        r = new rand(12345);
        count = 0;
    }

    public void deltext(double e, message x) {
        Continue(e);
        entity val;
        for (int i = 0; i < x.size(); i++) {
            if (messageOnPort(x, "in1", i)) {
                val = x.getValOnPort("in1", i);
                if (val.getName().compareTo("startMolecule") == 0) {
                    molecule = true;
                    arrived = this.getSimulationTime();
                    System.out.println(val.getName() + " arrived at time:" + arrived);
                    if (enzyme)
                        holdIn("active", 5);
                    else
                        holdIn("Waiting", 10);
                }
                //numOfarrivingcars++;
            }
            if (messageOnPort(x, "in2", i)) {
                val = x.getValOnPort("in2", i);
                if (val.getName().compareTo("enzymeEntry") == 0) {
                    enzyme = true;
                    solved = this.getSimulationTime();
                    if (molecule)
                        holdIn("active", 5);
                    else
                        holdIn("Waiting", 10);
                    System.out.println(val.getName() + " is finished at time:" + solved);
                }
                //numOfFinishedCars++;
            }
        }
    }


    public void deltint() {
        if (phaseIs("active")) {
            messageToSend = "startProcess";
            enzyme = false;
            molecule = false;
            out();
            passivate();
        } else if (phaseIs("passive")) {
            System.out.println("the total service time is: " + (solved - arrived));
            passivate();
        }

    }

    @Override
    public message out() {
        //System.out.println(name+" out count "+count);
        message m = new message();
        //content con = makeContent("out", new entity("car" + count));
        content con = makeContent("out1", new InputEntity(messageToSend, 1));
        m.add(con);

        return m;
    }

    private String getMessageOnPortZero(message x) {
        return x.getValOnPort("in1", 0).toString();
    }

    private String getMessageOnPortOne(message x) {
        return x.getValOnPort("in2", 0).toString();
    }

}
