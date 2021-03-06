/*      Copyright 2002 Arizona Board of regents on behalf of 
 *                  The University of Arizona 
 *                     All Rights Reserved 
 *         (USE & RESTRICTION - Please read COPYRIGHT file) 
 * 
 *  Version    : DEVSJAVA 2.7 
 *  Date       : 08-15-02 
 */

package genDevs.simulation.realTime;

import genDevs.simulation.AtomicSimulatorInterface;


public interface RTSimulatorInterface extends AtomicSimulatorInterface, Runnable {

    public long timeInSecs();

    public long timeInMillis();

    public void setTN();

    public double getTN();

//public void setCurrentTime();
//public double getCurrentTime();
//public void setLastTime();
//public double getLastTime();

    public void stopSimulate();

    public void sendMessages();
}


