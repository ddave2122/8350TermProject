/*      Copyright 2002 Arizona Board of regents on behalf of 
 *                  The University of Arizona 
 *                     All Rights Reserved 
 *         (USE & RESTRICTION - Please read COPYRIGHT file) 
 * 
 *  Version    : DEVSJAVA 2.7 
 *  Date       : 08-15-02 
 */


package GenDevsTest;

import simView.ViewableDigraph;

import java.awt.*;

public class ActCoupledModel extends ViewableDigraph {
    public ActCoupledModel() {
        super("actCoupledModel");
        ActModel first = new ActModel("first", 8);
        ActModel second = new ActModel("second", 8);
        add(first);
        add(second);
        addCoupling(first, "out", second, "in");

        first.setPreferredLocation(new Point(21, 94));
        second.setPreferredLocation(new Point(238, 94));
    }
}
