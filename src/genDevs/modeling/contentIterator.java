package genDevs.modeling;

import java.util.Iterator;

class contentIterator implements ContentIteratorInterface {
    private Iterator it;

    public contentIterator(MessageInterface m) {
        it = m.iterator();
    }

    public boolean hasNext() {
        return it.hasNext();
    }

    public ContentInterface next() {
        return (ContentInterface) it.next();
    }

}