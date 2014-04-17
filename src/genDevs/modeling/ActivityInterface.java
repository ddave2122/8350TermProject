package genDevs.modeling;

import GenCol.entity;
import genDevs.simulation.CoupledSimulatorInterface;

public interface ActivityInterface extends Runnable {
    public void setSimulator(CoupledSimulatorInterface sim);

    public double getProcessingTime();

    public String getName();

    public void kill();

    public void start();

    public entity computeResult();
}