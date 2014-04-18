package genDevs.modeling;

import java.util.Iterator;


public class componentIterator {
    private Iterator it;

    public componentIterator(ComponentsInterface c) {
        it = c.iterator();
    }

    public boolean hasNext() {
        return it.hasNext();
    }

    public IOBasicDevs nextComponent() {
        return (IOBasicDevs) it.next();
    }   //  ???  Problem
}
