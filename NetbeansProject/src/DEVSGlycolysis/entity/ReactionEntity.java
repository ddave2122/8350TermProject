package DEVSGlycolysis.entity;

import DEVSGlycolysis.atomic.Product;
import GenCol.entity;

/**
 * Created with IntelliJ IDEA.
 * User: nick
 * Date: 4/21/14
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReactionEntity extends entity {
    private Product.ProductType product;
    private double concentration;
    private double reactionTime;

    public ReactionEntity() {
        this("Glucose", Product.ProductType.Glucose, 1.0, 1.0);
    }

    public ReactionEntity(String name, Product.ProductType product, double concentration, double time) {
        super(name);
        this.product = product;
        this.concentration = concentration;
        this.reactionTime = time;
    }

    public Product.ProductType getProduct() {
        return this.product;
    }

    public double getConcentration() {
        return this.concentration;
    }

    public double getReactionTime() {
        return this.reactionTime;
    }

    @Override
    public String toString() {
        return product.toString() + "_" + concentration + "_" + reactionTime;
    }

}
