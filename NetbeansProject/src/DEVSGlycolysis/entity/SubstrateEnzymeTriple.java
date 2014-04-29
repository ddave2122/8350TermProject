package DEVSGlycolysis.entity;

import DEVSGlycolysis.atomic.CoSubstrate;
import DEVSGlycolysis.atomic.EnzymeType;
import DEVSGlycolysis.atomic.Product;
import GenCol.entity;

/**
 * Created with IntelliJ IDEA.
 * User: nicholas
 * Date: 4/20/14
 * Time: 8:18 PM
 */
public class SubstrateEnzymeTriple extends entity {
    private Product.ProductType substrateType;
    private CoSubstrate.CoSubstrateType cosubstrateType;
    private EnzymeType enzymeType;
    private double concentration;

    public SubstrateEnzymeTriple() {
        this("SubstrateEnzymeTriple", Product.ProductType.Glucose, 1.0,
                CoSubstrate.CoSubstrateType.ATP, EnzymeType.Hexokinase);
    }

    public SubstrateEnzymeTriple(String name, Product.ProductType substrateType,
                                 double concentration, CoSubstrate.CoSubstrateType cossType, EnzymeType enzymeType) {
        super(name);
        this.substrateType = substrateType;
        this.concentration = concentration;
        this.cosubstrateType = cossType;
        this.enzymeType = enzymeType;
    }

    public Product.ProductType getSubstrateType() {
        return this.substrateType;
    }

    public CoSubstrate.CoSubstrateType getCoSubstrateType() {
        return this.cosubstrateType;
    }

    public EnzymeType getEnzymeType() {
        return this.enzymeType;
    }

    public double getProductConcentration() {
        return this.concentration;
    }

    public void setConcentration(double d) {
        this.concentration = d;
    }

    @Override
    public String toString() {
        return substrateType.toString() + "_" + cosubstrateType.toString() + "_" + enzymeType.toString();
    }
}
