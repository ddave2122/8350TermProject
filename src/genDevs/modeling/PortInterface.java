package genDevs.modeling;

import GenCol.EntityInterface;

import java.util.Iterator;


public interface PortInterface extends EntityInterface {
}

interface portIterator extends Iterator {
    public PortInterface nextPort();
}

