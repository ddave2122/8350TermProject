package genDevs.modeling;

import java.util.Collection;


public interface MessageInterface extends Collection {
    public boolean onPort(PortInterface port, ContentInterface c);

    public Object getValOnPort(PortInterface port, ContentInterface c);

    public void print();
/* examples of using ensembleBag approach */
//public ensembleBag getPortNames();
//public ensembleBag valuesOnPort(String portName);

    // Jeff
    ContentIteratorInterface mIterator();
// Jeff
}

