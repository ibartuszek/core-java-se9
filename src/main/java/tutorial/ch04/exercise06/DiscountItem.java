package tutorial.ch04.exercise06;

import java.util.Objects;

public class DiscountItem extends Item {

    private double discount;

    public DiscountItem(final String description, final double price, final double discount) {
        super(description, price);
        this.discount = discount;
    }

    @Override
    public boolean equals(final Object o) {
        //        if (this == o) {
        //            return true;
        //        }
        //        if (o == null || getClass() != o.getClass())
        //            return false;
        if (!super.equals(o))
            return false;
        if (getClass() != o.getClass()) {
            return false;
        }
        DiscountItem that = (DiscountItem) o;
        return Double.compare(that.discount, discount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), discount);
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString.substring(0, superString.length() - 1)
            + ", discount=" + discount
            + '}';
    }
}
