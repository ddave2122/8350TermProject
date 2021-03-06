package genDevs.modeling;

import GenCol.EntityInterface;
import genDevs.simulation.coordinator;


public interface coupledDevs extends EntityInterface {

    public void add(IODevs d);

    public void addCoupling(IODevs src, String p1, IODevs dest, String p2);

    public IODevs withName(String nm);

    public ComponentsInterface getComponents();

    public couprel getCouprel();

    public void setCoordinator(coordinator coordinator);

    public coordinator getCoordinator();

    //by saurabh
    public void printComponents();

    public void printCouprel(couprel cr);

    public void removeCoupling(IODevs src, String p1, IODevs dest, String p2);
}



