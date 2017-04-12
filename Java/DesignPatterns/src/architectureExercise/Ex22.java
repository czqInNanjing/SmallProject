package architectureExercise;

import java.util.List;

/**
 * @author Qiang
 * @since 31/03/2017
 */
public class Ex22 {




}


class MyProduct {
    Promotion promotion;
    double price;
    int storeSize;
    int level;

    public MyProduct(Promotion promotion, double price, int storeSize, int level) {
        this.promotion = promotion;
        this.price = price;
        this.storeSize = storeSize;
        this.level = level;
    }

    List<MyProduct> getGift() {
        return promotion.getGift();
    }

    double getPrice(){
        return price*promotion.getRation();
    }

    boolean isNeedy () {
        return storeSize <= level*promotion.getScale();
    }

}

interface Promotion{
    double getRation();
    int getScale();
    List<MyProduct> getGift();
}


class NuLLPromotion implements Promotion {

    @Override
    public double getRation() {
        return 1;
    }

    @Override
    public int getScale() {
        return 1;
    }

    @Override
    public List<MyProduct> getGift() {
        return null;
    }
}