/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DEVSJAVALab;

import simView.ViewableAtomic;
import simView.ViewableComponent;
import simView.ViewableDigraph;

import java.awt.*;

/**
 * @author David
 */
public class ScreenSaverProgram extends ViewableDigraph {

    public ScreenSaverProgram() {
        this("ScreebSaverProgram");
    }

    public ScreenSaverProgram(String nm) {
        super(nm);
        ScreenSaverProgramConstructor();
    }

    public void ScreenSaverProgramConstructor() {
        this.addOutport("out");

        ViewableAtomic cpu = new CPU("CPU", 7);
        ViewableAtomic screen = new Screen("Screen", 30);
        ViewableAtomic keyboardMouse = new KeyboardMouse("KeyboardMouse");
        ViewableAtomic screenSaver = new ScreenSaver("ScreenSaver");

        add(cpu);
        add(screen);
        add(keyboardMouse);
        add(screenSaver);

        addCoupling(keyboardMouse, "out", screenSaver, "in");
        addCoupling(screenSaver, "out", cpu, "in");
        addCoupling(screenSaver, "out", screen, "in");

    }

    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView() {
        preferredSize = new Dimension(625, 341);
        if ((ViewableComponent) withName("Screen") != null)
            ((ViewableComponent) withName("Screen")).setPreferredLocation(new Point(69, 155));
        if ((ViewableComponent) withName("ScreenSaver") != null)
            ((ViewableComponent) withName("ScreenSaver")).setPreferredLocation(new Point(302, 34));
        if ((ViewableComponent) withName("KeyboardMouse") != null)
            ((ViewableComponent) withName("KeyboardMouse")).setPreferredLocation(new Point(62, 57));
        if ((ViewableComponent) withName("CPU") != null)
            ((ViewableComponent) withName("CPU")).setPreferredLocation(new Point(323, 166));
    }
}
