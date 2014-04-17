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
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class DNReaction extends ViewableDigraph {

    public DNReaction(){
        this("Reaction");
    }

    public DNReaction(String nm){
        super(nm);
        DNReactionConstructor();
    }
    
    public void DNReactionConstructor() {
        this.addOutport("out1");
        this.addInport("in1");
        
        ViewableAtomic dNFixing = new DNFixing("Fixing");
        ViewableAtomic dNTransformation = new DNTransformation("Transformation");
        ViewableAtomic dNRelease = new DNRelease("Release");

        add(dNFixing);
        add(dNTransformation);
        add(dNRelease);

        addCoupling(dNFixing,"out1",dNTransformation,"in1");
        addCoupling(dNTransformation,"out1",dNRelease,"in1");

    }

    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(625, 331);
        if((ViewableComponent)withName("Release")!=null)
             ((ViewableComponent)withName("Release")).setPreferredLocation(new Point(346, 212));
        if((ViewableComponent)withName("Transformation")!=null)
             ((ViewableComponent)withName("Transformation")).setPreferredLocation(new Point(177, 226));
        if((ViewableComponent)withName("Fixing")!=null)
             ((ViewableComponent)withName("Fixing")).setPreferredLocation(new Point(34, 123));
    }
}
