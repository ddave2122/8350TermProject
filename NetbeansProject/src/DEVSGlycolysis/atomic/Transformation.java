package DEVSGlycolysis.atomic;

import DEVSGlycolysis.entity.SubstrateEnzymeTriple;
import DEVSJAVALab.InputEntity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 * Created with IntelliJ IDEA.
 * User: nick
 * Date: 4/17/14
 * Time: 11:02 AM
 *
 * NOTE
 * Transformation models the time required for a reaction to take place
 *
 */
public class Transformation extends ViewableAtomic {
    private double int_gen_time;
    private rand r;
    private int count;

    private String messageToSend;
    private Product.ProductType release;

    private static final String inPort = "in1";
    private static final String outPort = "out1";

    public Transformation() {
        this("Glucose");
    }

    public Transformation(String name) {
        super(name);
        addInport(inPort);
        addOutport(outPort);
    }

    public void initialize() {
        holdIn("passive", int_gen_time);
        r = new rand(12345);
        count = 0;
    }

    public void deltext(double e, message x) {
        Continue(e);

        if (messageOnPort(x, inPort, 0)) {
            SubstrateEnzymeTriple val = (SubstrateEnzymeTriple)x.getValOnPort(inPort, 0);
            AtomicEnzyme.EnzymeType eType = val.getEnzymeType();
            Product.ProductType pType = val.getSubstrateType();
            CoSubstrate.CoSubstrateType cssType = val.getCoSubstrateType();

            determineProduct(eType, pType, cssType);

            if (getMessageOnPortZero(x).equals("movement"))
                holdIn("passive", 300);  //Hold in active for 5 minutes
            else if (getMessageOnPortZero(x).equals("active"))
                holdIn("active", 1800);  //Hold in active for 30 minutes
            else if (getMessageOnPortZero(x).equals("hibernate"))
                holdIn("hibernate", Integer.MAX_VALUE);
            else
                System.out.println("UNKNOWN MESSAGE: " + getMessageOnPortZero(x));
        }
    }

    private Product.ProductType determineProduct(AtomicEnzyme.EnzymeType eType, Product.ProductType pType, CoSubstrate.CoSubstrateType cssType) {
        if (eType == AtomicEnzyme.EnzymeType.Hexokinase && pType == Product.ProductType.Glucose &&
                cssType == CoSubstrate.CoSubstrateType.ATP) {

            return Product.ProductType.Glucose6Pphosphate;

        } else if (eType == AtomicEnzyme.EnzymeType.Phosphoglucose_isomerase &&
                pType == Product.ProductType.Glucose6Pphosphate &&
                cssType == CoSubstrate.CoSubstrateType.None) {

            return Product.ProductType.Fructose6Phosphate;

        } else if (eType == AtomicEnzyme.EnzymeType.Phosphofructokinase &&
                pType == Product.ProductType.Fructose6Phosphate &&
                cssType == CoSubstrate.CoSubstrateType.ATP) {

            return Product.ProductType.Fructose1_6BiPhosphate;

        } else if (eType == AtomicEnzyme.EnzymeType.Aldolase &&
                pType == Product.ProductType.Fructose1_6BiPhosphate &&
                cssType == CoSubstrate.CoSubstrateType.None) {

            return Product.ProductType.DiHydroxideAcetonePhosphate;
        }
        return Product.ProductType.DiHydroxideAcetonePhosphate;
    }

    public void deltint() {
        if (phaseIs("passive")) {
            messageToSend = "on";
            out();
        } else if (phaseIs("active")) {
            messageToSend = "sleep";
            out();
        } else if (phaseIs("hibernate")) {
            messageToSend = "hibernate";
            out();
        } else
            System.out.println("UNKNOWN PHASE: " + getPhase());
    }


    public message out() {

        message m = new message();

        content con = makeContent(outPort, new InputEntity(messageToSend, 1));
        m.add(con);

        return m;
    }

    private String getMessageOnPortZero(message x) {
        return x.getValOnPort(inPort, 0).toString();
    }
}
