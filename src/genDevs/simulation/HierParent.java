package genDevs.simulation;

interface HierParent {
    public void setParent(CoupledCoordinatorInterface p);  //bpz

    public void setRootParent(CoordinatorInterface p);

    public CoupledCoordinatorInterface getParent();

    public CoordinatorInterface getRootParent();
}
