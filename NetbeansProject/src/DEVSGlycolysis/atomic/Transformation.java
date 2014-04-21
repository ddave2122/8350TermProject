package DEVSGlycolysis.atomic;

import DEVSGlycolysis.entity.ReactionEntity;
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

    private Product.ProductType release;
    private double concentration;
    private double reactionTime;

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
        passivate();
    }

    public void deltext(double e, message x) {
        Continue(e);

        if (messageOnPort(x, inPort, 0)) {
            SubstrateEnzymeTriple val = (SubstrateEnzymeTriple)x.getValOnPort(inPort, 0);
            AtomicEnzyme.EnzymeType eType = val.getEnzymeType();
            Product.ProductType pType = val.getSubstrateType();
            CoSubstrate.CoSubstrateType cssType = val.getCoSubstrateType();

            try {

                this.release = determineProduct(eType, pType, cssType);

            } catch (Exception exp) {
                System.err.println(exp.getMessage());
            } finally {
                passivate();
            }
        }
    }

    public void deltint() {
        if (phaseIs("passive")) {
            out();
        } else if (phaseIs("active")) {
            out();
        } else if (phaseIs("hibernate")) {
            out();
        } else
            System.out.println("UNKNOWN PHASE: " + getPhase());
    }

    private Product.ProductType determineProduct(AtomicEnzyme.EnzymeType eType,
                                                 Product.ProductType pType,
                                                 CoSubstrate.CoSubstrateType cssType)
            throws Exception
    {
        Product.ProductType newProduct;

        if (eType == AtomicEnzyme.EnzymeType.Hexokinase && pType == Product.ProductType.Glucose &&
                cssType == CoSubstrate.CoSubstrateType.ATP) {

            newProduct = Product.ProductType.Glucose6Pphosphate;

        } else if (eType == AtomicEnzyme.EnzymeType.Phosphoglucose_isomerase &&
                pType == Product.ProductType.Glucose6Pphosphate &&
                cssType == CoSubstrate.CoSubstrateType.None) {

            newProduct = Product.ProductType.Fructose6Phosphate;

        } else if (eType == AtomicEnzyme.EnzymeType.Phosphofructokinase &&
                pType == Product.ProductType.Fructose6Phosphate &&
                cssType == CoSubstrate.CoSubstrateType.ATP) {

            newProduct = Product.ProductType.Fructose1_6BiPhosphate;

        } else if (eType == AtomicEnzyme.EnzymeType.Aldolase &&
                pType == Product.ProductType.Fructose1_6BiPhosphate &&
                cssType == CoSubstrate.CoSubstrateType.None) {

            newProduct = Product.ProductType.DiHydroxideAcetonePhosphate;

        } else if (eType == AtomicEnzyme.EnzymeType.Glyceraldehydephosphatedehyrdogenase &&
                pType == Product.ProductType.GlycerAlderhyde3Phosphate &&
                cssType == CoSubstrate.CoSubstrateType.NADPlus) {

            newProduct = Product.ProductType._1_3BiPhosphoGlycerate;

        } else if (eType == AtomicEnzyme.EnzymeType.Phosphoglyceratekinase &&
                pType == Product.ProductType._1_3BiPhosphoGlycerate &&
                cssType == CoSubstrate.CoSubstrateType.ADP) {

            newProduct = Product.ProductType._3PhosphoGlycerate;

        } else if (eType == AtomicEnzyme.EnzymeType.Phosphoglyceratemutase &&
                pType == Product.ProductType._3PhosphoGlycerate &&
                cssType == CoSubstrate.CoSubstrateType.None) {

            newProduct = Product.ProductType._2PhosphoGlycerate;

        } else if (eType == AtomicEnzyme.EnzymeType.Phosphopyruvatehydratase &&
                pType == Product.ProductType._2PhosphoGlycerate &&
                cssType == CoSubstrate.CoSubstrateType.None) {

            newProduct = Product.ProductType.PhosphoenolPyruvate;

        } else if (eType == AtomicEnzyme.EnzymeType.Pyruvatekinase &&
                pType == Product.ProductType.PhosphoenolPyruvate &&
                cssType == CoSubstrate.CoSubstrateType.ADP) {

            newProduct = Product.ProductType.Pyruvate;
        } else {
            //error
            throw new Exception("bad transformation!");
        }
        return newProduct;
    }




    public message out() {
        message m = new message();
        ReactionEntity rEnt = new ReactionEntity("ReactionEntity", this.release, this.concentration, this.reactionTime);
        content con = makeContent(outPort, rEnt);
        m.add(con);
        return m;
    }

}
