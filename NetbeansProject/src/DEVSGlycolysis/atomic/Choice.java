package DEVSGlycolysis.atomic;

import DEVSGlycolysis.entity.SubstrateEnzymeTriple;
import DEVSGlycolysis.entity.SubstratePair;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 * Created with IntelliJ IDEA.
 * User: nick
 * Date: 4/17/14
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class Choice extends ViewableAtomic {
    private double int_gen_time;
    private rand r;
    private int count;

    private SubstratePair pair;
    private SubstrateEnzymeTriple triple;

    public static final String inPort = "in1";
    public static final String outPort1 = "out1";
    public static final String outPort2 = "out2";

    public Choice() {
        this("Choice", 4);
    }

    public Choice(String name, double period) {
        super(name);
        addInport(inPort);
        addOutport(outPort1);
        addOutport(outPort2);
        addNameTestInput(inPort, "Reaction");

        int_gen_time = period ;
    }

    @Override
    public void initialize() {
        holdIn("passive", int_gen_time);
        r = new rand(12345);
        count = 0;
        passivate();
    }

    @Override
    public void deltext(double e, message x) {
        Continue(e);

        if (messageOnPort(x, inPort, 0)) {
            this.pair = (SubstratePair) x.getValOnPort(inPort, 0);
            Product.ProductType pType = this.pair.getProductType();
            CoSubstrate.CoSubstrateType cType = this.pair.getCoSSType();

            switch (pType) {
                case Glucose :
                    switch (cType) {
                        case ATP:
                            this.triple = new SubstrateEnzymeTriple("Triple", pType, cType, AtomicEnzyme.EnzymeType.Hexokinase);
                            break;
                        default:
                            System.err.println("Bad config!");
                            break;
                    }
                    break;
                case Glucose6Phosphate:
                    switch (cType) {
                        case None:
                            this.triple = new SubstrateEnzymeTriple("Triple", pType, cType, AtomicEnzyme.EnzymeType.Phosphoglucose_isomerase);
                            break;
                        default:
                            System.err.println("Bad config!");
                            break;
                    }
                    break;
                case Fructose6Phosphate:
                    switch (cType) {
                        case ATP:
                            this.triple = new SubstrateEnzymeTriple("Triple", pType, cType, AtomicEnzyme.EnzymeType.Phosphofructokinase);
                            break;
                        default:
                            System.err.println("Bad config!");
                            break;
                    }
                    break;
                case Fructose1_6BiPhosphate:
                    switch (cType) {
                        case None:
                            this.triple = new SubstrateEnzymeTriple("Triple", pType, cType, AtomicEnzyme.EnzymeType.Aldolase);
                            break;
                        default:
                            System.err.println("Bad config!");
                            break;
                    }
                    break;
                case DiHydroxideAcetonePhosphate:
                    switch (cType) {
                        case None:
                            this.triple = new SubstrateEnzymeTriple("Triple", pType, cType, AtomicEnzyme.EnzymeType.Triosephosphateisomerase);
                            break;
                        default:
                            System.err.println("Bad config!");
                            break;
                    }
                    break;
                case GlycerAlderhyde3Phosphate:
                    switch (cType) {
                        case NADPlus:
                            this.triple = new SubstrateEnzymeTriple("Triple", pType, cType, AtomicEnzyme.EnzymeType.Glyceraldehydephosphatedehyrdogenase);
                            break;
                        default:
                            System.err.println("Bad config!");
                            break;
                    }
                    break;
                case _1_3BiPhosphoGlycerate:
                    switch (cType) {
                        case ADP:
                            this.triple = new SubstrateEnzymeTriple("Triple", pType, cType, AtomicEnzyme.EnzymeType.Phosphoglyceratekinase);
                            break;
                        default:
                            System.err.println("Bad config!");
                            break;
                    };
                    break;
                case _3PhosphoGlycerate:
                    switch (cType) {
                        case None:
                            this.triple = new SubstrateEnzymeTriple("Triple", pType, cType, AtomicEnzyme.EnzymeType.Phosphoglyceratemutase);
                            break;
                        default:
                            System.err.println("Bad config!");
                            break;
                    }
                    break;
                case _2PhosphoGlycerate:
                    switch (cType) {
                        case None:
                            this.triple = new SubstrateEnzymeTriple("Triple", pType, cType, AtomicEnzyme.EnzymeType.Phosphopyruvatehydratase);
                            break;
                        default:
                            System.err.println("Bad config!");
                            break;
                    };
                    break;
                case PhosphoenolPyruvate:
                    switch (cType) {
                        case ADP:
                            this.triple = new SubstrateEnzymeTriple("Triple", pType, cType, AtomicEnzyme.EnzymeType.Pyruvatekinase);
                            break;
                        default:
                            System.err.println("Bad config!");
                            break;
                    }
                    break;
            }
            holdIn("active", 1);
        }
    }

    @Override
    public void deltint() {
        if (phaseIs("active")) {
            out();
            passivate();
        }  
        else
            System.out.println("UNKNOWN PHASE: " + getPhase());
    }

    @Override
    public message out() {
        message m = new message();

        if (this.triple.getEnzymeType() == AtomicEnzyme.EnzymeType.Aldolase) {
            m.add(makeContent(outPort1, this.triple));
            m.add(makeContent(outPort2, this.triple));
        } else {
            m.add(makeContent(outPort1, this.triple));
        }

        return m;
    }

    private String getMessageOnPortZero(message x) {
        return x.getValOnPort(inPort, 0).toString();
    }
}
