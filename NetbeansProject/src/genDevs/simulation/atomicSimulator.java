package genDevs.simulation;

import GenCol.EntityInterface;
import GenCol.entity;
import genDevs.modeling.*;
import util.Logging;

public class atomicSimulator
        implements AtomicSimulatorInterface {//for usual devs

    protected double tL, tN;
    public MessageInterface input, output;
    protected IOBasicDevs myModel;

    public atomicSimulator() {
    }

    public atomicSimulator(IOBasicDevs atomic) {
        myModel = atomic;
        input = new message();
        output = new message();
    }

    public double nextTN() {
        return tN;
    }

    public boolean equalTN(double t) {
        return t == tN;
    }

    public double getTN() {
        return tN;
    }

    public double getTL() {
        return tL;
    }

    public synchronized MessageInterface getOutput() {
        return output;
    }

    //
    public synchronized MessageInterface getInput() {
        return input;
    }


    public Double nextTNDouble() {
        return new Double(nextTN());
    }

    public synchronized void showModelState() {
        myModel.showState();
    }

    public synchronized void initialize() { //for non real time usage, assume the time begins at 0
        myModel.initialize();
        tL = 0.0;
        tN = myModel.ta();
        Logging.log("INITIALIZATION, time: " + tL + ", next event at: " + tN,
                Logging.full);
        myModel.showState();
    }

    public synchronized void initialize(Double d) {
        initialize(d.doubleValue());
    }

    public synchronized void initialize(double currentTime) {     // for real time usage
        myModel.initialize();
        tL = currentTime;
        tN = tL + myModel.ta();
        Logging.log("INITIALIZATION, time: " + tL + ", next event at: " + tN,
                Logging.full);
        myModel.showState();
    }

    public synchronized void DeltFunc(Double d) {
        DeltFunc(d.doubleValue());
    }

    public synchronized void DeltFunc(double t) {
        wrapDeltfunc(t, new message());
    }

    public synchronized void wrapDeltfunc(double t) {
        wrapDeltfunc(t, input); //changed to work with activity
        input = new message();
    }

    public synchronized void wrapDeltfunc(double t, MessageInterface x) {
        if (x == null) {
            System.out.println("ERROR RECEIVED NULL INPUT  " + myModel.toString());
            return;
        }
        if (x.isEmpty() && !equalTN(t)) {
            return;
        } else if ((!x.isEmpty()) && equalTN(t)) {
            double e = t - tL;
            myModel.deltcon(e, x);
        } else if (equalTN(t)) {
            myModel.deltint();
        } else if (!x.isEmpty()) {
            double e = t - tL;
            myModel.deltext(e, x);
        }
        wrapDeltfuncHook2();
        tL = t;
        tN = tL + myModel.ta();
    }

    public void computeInputOutput(Double d) {
        computeInputOutput(d.doubleValue());
    }

    public void computeInputOutput(double t) {
        if (equalTN(t)) {
            output = myModel.Out();
        } else {
            output = new message();//bpz
        }

        computeInputOutputHook1();
    }

    public void simulate(int numIter) {
        int i = 1;
        tN = nextTN();
        while ((tN < DevsInterface.INFINITY) && (i <= numIter)) {
            Logging.log("ITERATION " + i + " ,time: " + tN, Logging.full);
            computeInputOutput(tN);
            showOutput();
            DeltFunc(tN);
            tL = tN;
            tN = nextTN();
            showModelState();
            i++;
        }
        System.out.println("Terminated Normally at ITERATION " + i + " ,time: " + tN);
    }

    public void simulate(Integer i) {
        simulate(i.intValue());
    }

    public synchronized void showOutput() {
        if (output == null) return;
// System.out.println("Ouput -------------->");
        if (!output.isEmpty())
            output.print();
    }

    public MessageInterface makeMessage() {
        return new message();
    }

    public void simInject(double e, PortInterface p, EntityInterface value) {
        MessageInterface m = makeMessage();
        m.add(myModel.makeContent(p, value));
        simInject(e, m);
    }

    public void simInject(double e, String portName, entity value) {
        //  for use in usual devs
        simInject(e, new port(portName), value);
    }

    public void simInject(double e, MessageInterface m) {
        double t = tL + e;
        if (e == myModel.ta()) {
            System.out.println("THIS IS A CONFLUENT SITUATION ");
            this.computeInputOutput(t);
            ((coupledSimulator) this).sendMessages();  // Xiaolin Hu, Oct. 19, 2003
            System.out.println("The model output is ");
            showOutput();
            simInjectHook3(getOutput().toString());
        }
        if (e <= myModel.ta()) {
            simInjectHook1(e);
            wrapDeltfunc(t, m);
            System.out.println("Time: " + t + " ,input injected: ");
            m.print();
            myModel.showState();
            simInjectHook2(t);
// update added or removed simulators during wrapDeltfunc() -- Xiaolin, Oct 19, 2003
            coordinator c = null;
            if (((coupledSimulator) this).getParent() != null) c = (coordinator) ((coupledSimulator) this).getParent();
            else if (((coupledSimulator) this).getRootParent() != null)
                c = (coordinator) ((coupledSimulator) this).getRootParent();
            if (c != null) c.updateChangedSimulators();

        } else System.out.println("Time: " + tL + " ,ERROR input rejected : elapsed time " + e + " is not in bounds.");
    }

    /**
     * These are hooks needed by the simulation viewer extension of GenDevs,
     * People seeking to create their own standard GenDevs implementation
     * may safely ignore their presence.
     */
    protected void wrapDeltfuncHook2() {
    }

    protected void simInjectHook1(double e) {
    }

    protected void simInjectHook2(double newTime) {
    }

    protected void simInjectHook3(String mess) {
    }

    protected void computeInputOutputHook1() {
    }

    public IOBasicDevs getModel() {
        return myModel;
    }
}
