package DEVSGlycolysis.atomic;

import DEVSGlycolysis.entity.ReactionEntity;
import DEVSGlycolysis.entity.SubstratePair;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
import statistics.rand;

/**
 * Created with IntelliJ IDEA.
 * User: nick
 * Date: 4/17/14
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class CoSubstrate extends ViewableAtomic {
    public enum CoSubstrateType {ATP, NADPlus, ADP, None};

    private double int_gen_time;
    private rand r;
    private int count;

    private ReactionEntity reactionEntity;
    private CoSubstrateType coSubstrate;

    private static final String inPort1 = "in1";
    private static final String inPort2 = "in2";
    private static final String outPort = "out1";

    public CoSubstrate() {
        this("Screen");
    }

    public CoSubstrate(String name) {
        super(name);
        addInport(inPort2);
        addInport(inPort1);
        addOutport(outPort);
    }

    @Override
    public void initialize() {
        holdIn("passive", 11);
        r = new rand(12345);
        count = 0;
        passivate();
    }
    
    @Override
    public void deltext(double e, message x) {
        Continue(e);

        for (int i = 0; i < x.size(); i++) {
            if (messageOnPort(x, inPort2, i)) {
                reactionEntity = (ReactionEntity)x.getValOnPort("in2", i);
                switch(reactionEntity.getProduct())
                {
                    case Glucose :
                        this.coSubstrate = CoSubstrateType.ATP;
                        break;
                    case Glucose6Phosphate:
                        this.coSubstrate = CoSubstrateType.None;
                        break;
                    case Fructose6Phosphate:
                        this.coSubstrate = CoSubstrateType.ATP;
                        break;
                    case Fructose1_6BiPhosphate:
                        this.coSubstrate = CoSubstrateType.None;
                        break;
                    case DiHydroxideAcetonePhosphate:
                        this.coSubstrate = CoSubstrateType.None;
                        break;
                    case GlycerAlderhyde3Phosphate:
                        this.coSubstrate = CoSubstrateType.NADPlus;
                        break;
                    case _1_3BiPhosphoGlycerate:
                        this.coSubstrate = CoSubstrateType.ADP;
                        break;
                    case _3PhosphoGlycerate:
                        this.coSubstrate = CoSubstrateType.None;
                        break;
                    case _2PhosphoGlycerate:
                        this.coSubstrate = CoSubstrateType.None;
                        break;
                    case PhosphoenolPyruvate:
                        this.coSubstrate = CoSubstrateType.ADP;
                        break;
                }
                holdIn("active", 1);
            }
            if (messageOnPort(x, inPort1, i)) {

                reactionEntity = (ReactionEntity)x.getValOnPort(inPort1, i);
                
                if (reactionEntity.getProduct() == Product.ProductType.Glucose) {
                    this.coSubstrate = CoSubstrateType.ATP;
                        holdIn("active", 1);
                }
            }
        }
    }
    @Override
    public void deltint() {
        if (phaseIs("active")) {
            out();
            passivate();
        } else if (phaseIs("passive")) {
            passivate();
        } else
            System.out.println("UNKNOWN PHASE: " + getPhase());
    }

    @Override
    public message out() {
        message m = new message();
        content con = makeContent(outPort,
                        new SubstratePair("name", this.reactionEntity.getProduct(), this.coSubstrate));
        m.add(con);
        return m;
    }

}