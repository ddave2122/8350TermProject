package DEVSJAVALab;

import java.awt.Dimension;
import java.awt.Point;
import simView.ViewableAtomic;
import simView.ViewableComponent;
import simView.ViewableDigraph;

/**
 * Created with IntelliJ IDEA.
 * User: nick
 * Date: 4/17/14
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class DNEnzyme extends ViewableDigraph {

    
    public DNEnzyme(){
        this("Enzyme");
    }

    public DNEnzyme(String nm){
        super(nm);
        DNEnzymeConstructor();
    }
    
    public void DNEnzymeConstructor() {
        this.addOutport("out");
        this.addInport("in1");
        
        ViewableAtomic dNChoice = new DNChoice("Choice");
        ViewableAtomic dNAMEnzyme = new DNAMEnzyme("Enzyme");
        ViewableAtomic dNAldolase = new DNAldolase("Aldolase");        
        ViewableAtomic dNActiva = new DNActiva("Activa");

        add(dNChoice);
        add(dNAMEnzyme);
        add(dNAldolase);        
        add(dNActiva);


        addCoupling(dNChoice,"out1",dNAMEnzyme,"in1");
        addCoupling(dNChoice,"out2",dNAldolase,"in1");
        addCoupling(dNAMEnzyme,"out1",dNActiva,"in1");
        addCoupling(dNAldolase,"out1",dNActiva,"in2");

    }
    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(625, 341);
        if((ViewableComponent)withName("Activa")!=null)
             ((ViewableComponent)withName("Activa")).setPreferredLocation(new Point(349, 99));
        if((ViewableComponent)withName("Choice")!=null)
             ((ViewableComponent)withName("Choice")).setPreferredLocation(new Point(-9, 123));
        if((ViewableComponent)withName("Aldolase")!=null)
             ((ViewableComponent)withName("Aldolase")).setPreferredLocation(new Point(164, 234));
        if((ViewableComponent)withName("Enzyme")!=null)
             ((ViewableComponent)withName("Enzyme")).setPreferredLocation(new Point(166, 69));
    }
}
