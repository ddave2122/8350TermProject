package DEVSGlycolysis.entity;

import DEVSGlycolysis.atomic.AtomicEnzyme;
import DEVSGlycolysis.atomic.CoSubstrate;
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
    private AtomicEnzyme.EnzymeType enzymeType;

    public SubstrateEnzymeTriple() {
        this("SubstrateEnzymeTriple", Product.ProductType.Glucose,
                CoSubstrate.CoSubstrateType.ATP, AtomicEnzyme.EnzymeType.Hexokinase);
    }

    public SubstrateEnzymeTriple(String name, Product.ProductType substrateType,
                                 CoSubstrate.CoSubstrateType cossType, AtomicEnzyme.EnzymeType enzymeType) {
        super(name);
        this.substrateType = substrateType;
        this.cosubstrateType = cossType;
        this.enzymeType = enzymeType;
    }

    public Product.ProductType getSubstrateType() {
        return this.substrateType;
    }

    public CoSubstrate.CoSubstrateType getCoSubstrateType() {
        return this.cosubstrateType;
    }

    public AtomicEnzyme.EnzymeType getEnzymeType() {
        return this.enzymeType;
    }

    @Override
    public String toString() {
        return substrateType.toString() + "_" + cosubstrateType.toString() + "_" + enzymeType.toString();
    }
}
