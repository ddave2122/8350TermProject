/*      Copyright 2002 Arizona Board of regents on behalf of 
 *                  The University of Arizona 
 *                     All Rights Reserved 
 *         (USE & RESTRICTION - Please read COPYRIGHT file) 
 * 
 *  Version    : DEVSJAVA 2.7 
 *  Date       : 08-15-02 
 */ 


package GenDevsTest;

//import util.*;


import java.awt.*;
import java.util.*;
import GenCol.*;
import genDevs.modeling.*;
import genDevs.simulation.*;
import simView.*;

public class RegSeqGenr extends ViewableDigraph
{
    protected double interval = 900; // 90days *10 hrs
    protected int actualNum = 190;
    protected double actualMean = interval / actualNum;
    // (e.g. a registration every 3 hours)
    protected long seed = 40;
    public RegSeqGenr()
    {
        this("regSeqGenr", 1, 100, 900);
    }

    public RegSeqGenr(String nm, long seed, int actualNum, double interval)
    {
        super(nm);
        this.seed = seed;
        this.actualNum = actualNum;
        this.interval = interval;
        this.actualMean = interval / actualNum;
        addInport("in");
        addOutport("out");
        RegisterGenr rg1 = new RegisterGenr("1", seed, (int) Math.rint(.2 * actualNum), interval / 3);
        add(rg1);
        addCoupling(this, "start", rg1, "start");
        addCoupling(rg1, "out", this, "out");
        RegisterGenr rg2 = new RegisterGenr("2", seed + 1, (int) Math.rint(.1 * actualNum), interval / 3);
        add(rg2);
        addCoupling(rg2, "out", this, "out");
        addCoupling(rg1, "outStart", rg2, "start");
        RegisterGenr rg3 = new RegisterGenr("3", seed + 2, (int) Math.rint(.7 * actualNum), interval / 3);
        add(rg3);
        addCoupling(rg2, "outStart", rg3, "start");
        addCoupling(rg3, "out", this, "out");

        preferredSize = new Dimension(307, 248);
        rg1.setPreferredLocation(new Point(15, 25));
        rg2.setPreferredLocation(new Point(15, 98));
        rg3.setPreferredLocation(new Point(15, 171));
    }
    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(305, 248);
        ((ViewableComponent)withName("genr2")).setPreferredLocation(new Point(15, 98));
        ((ViewableComponent)withName("genr1")).setPreferredLocation(new Point(15, 25));
        ((ViewableComponent)withName("genr3")).setPreferredLocation(new Point(15, 171));
    }
}
