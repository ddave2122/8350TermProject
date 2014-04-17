package genDevs.modeling;

public interface ContentInterface {
    public PortInterface getPort();

    public String getPortName();

    public Object getValue();

    public boolean onPort(PortInterface port);
}



