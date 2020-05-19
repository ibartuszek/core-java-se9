package tutorial.ch04.exercise06;


//public class Item {
//    private String description;
//    private double price;
//...
//    public boolean equals(Object otherObject) {
//        // A quick test to see if the objects are identical
//        if (this == otherObject) return true;
//        // Must return false if the parameter is null
//        if (otherObject == null) return false;
//        // Check that otherObject is an Item
//        if (getClass() != otherObject.getClass()) return false;
//        // Test whether the instance variables have identical values
//        Item other = (Item) otherObject;
//        return Objects.equals(description, other.description)
//            && price == other.price;
//    }
//    public int hashCode() { ... } // See Section 4.2.3
//}

/**
 * Suppose that in Section 4.2.2, “The equals Method” (page 148), the
 * Item.equals method uses an instanceof test. Implement
 * DiscountedItem.equals so that it compares only the superclass if
 * otherObject is an Item, but also includes the discount if it is a
 * DiscountedItem. Show that this method preserves symmetry but fails to
 * be transitive—that is, find a combination of items and discounted items so that
 * x.equals(y) and y.equals(z), but not x.equals(z).
 */
public class DiscountItemExercise {

    public static void main(String[] args) {
        Item item = new Item("product1", 20.0D);
        Item discountItem = new DiscountItem("product1", 20.0D, 2.0D);
        Item discountItem2 = new DiscountItem("product1", 20.0D, 2.1D);
        System.out.println("item=" + item);
        System.out.println("discountItem=" + discountItem);
        System.out.println("discountItem2=" + discountItem2);
        System.out.println("item.equals(discountItem)=" + item.equals(discountItem));
        System.out.println("item.equals(discountItem2)=" + item.equals(discountItem2));
        System.out.println("discountItem.equals(discountItem2)=" + discountItem.equals(discountItem2));
    }

}
