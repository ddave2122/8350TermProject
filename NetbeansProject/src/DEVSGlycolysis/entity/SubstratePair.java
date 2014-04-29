package DEVSGlycolysis.entity;

import DEVSGlycolysis.atomic.CoSubstrate;
import DEVSGlycolysis.atomic.Product;
import GenCol.entity;

/**
 * Created with IntelliJ IDEA.
 * User: nicholas
 * Date: 4/20/14
 * Time: 5:44 PM
 */
public class SubstratePair extends entity {
    private CoSubstrate.CoSubstrateType cossType;
    private Product.ProductType ssType;
    private double productConcentration;

    public SubstratePair() {
        this("SubstratePair", Product.ProductType.Glucose, 1.0, CoSubstrate.CoSubstrateType.None);
    }

    public SubstratePair(String name, Product.ProductType pType, double conc, CoSubstrate.CoSubstrateType coSSType) {
        super(name);
        this.ssType = pType;
        this.productConcentration = conc;
        this.cossType = coSSType;
    }

    public CoSubstrate.CoSubstrateType getCoSSType() {
        return this.cossType;
    }

    public Product.ProductType getProductType() {
        return this.ssType;
    }

    public double getProductConcentration() {
        return this.productConcentration;
    }

    @Override
    public String toString() {
        return cossType.toString() + "_" + ssType.toString();
    }
}
