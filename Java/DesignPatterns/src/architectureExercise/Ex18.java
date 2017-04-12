package architectureExercise;

/**
 * @author Qiang
 * @since 31/03/2017
 */
public class Ex18 {
}



interface Visitor {
    void visit(Product product);
}


abstract class Product{
    abstract double getPrice();
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}


class CD extends Product {
    private double price;

    public CD(double price) {
        this.price = price;
    }

    @Override
    double getPrice() {
        return price;
    }
}

class Book extends Product{
    private double price;

    public Book(double price) {
        this.price = price;
    }
    @Override
    double getPrice() {
        return price;
    }
}


class DiscountVisitor implements Visitor{

    private double discount;
    private double originalPrice;

    public DiscountVisitor(double discount) {
        this.discount = discount;
    }

    @Override
    public void visit(Product product) {
        originalPrice = product.getPrice();


    }

    public double getAfterPrice() {
        return discount*originalPrice;
    }


}