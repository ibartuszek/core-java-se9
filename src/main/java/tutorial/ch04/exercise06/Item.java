package tutorial.ch04.exercise06;

import java.util.Objects;

public class Item {

    private String description;
    private double price;

    public Item(final String description, final double price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public boolean equals(Object otherObject) {
            if (this == otherObject) {
                return true;
            }
            if (otherObject == null) {
                return false;
            }
            if (!(otherObject instanceof Item)) {
            // if (getClass() != otherObject.getClass()) {
                return false;
            }
            Item other = (Item) otherObject;
            return Objects.equals(description, other.description)
                && price == other.price;
        }

        public int hashCode() {
            return Objects.hash(description, price);
        }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{"
            + "description='" + description + '\''
            + ", price=" + price
            + '}';
    }
}
