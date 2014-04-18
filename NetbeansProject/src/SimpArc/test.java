package SimpArc;


import genDevs.modeling.digraph;
import genDevs.simulation.realTime.TunableCoordinator;


public class test {

    protected static digraph testDig;

    public test() {
    }

    public static void main(String[] args) {
        testDig = new efp();
//      genDevs.simulation.coordinator cs = new genDevs.simulation.coordinator(testDig);

        TunableCoordinator cs = new TunableCoordinator(testDig);
        cs.setTimeScale(0.04);

        cs.initialize();
        cs.simulate(50);
    }
}