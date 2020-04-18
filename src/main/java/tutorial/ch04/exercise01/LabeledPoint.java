package tutorial.ch04.exercise01;

import java.util.Objects;

public class LabeledPoint extends Point {

    private final String label;

    public LabeledPoint(final double x, final double y, final String label) {
        super(x, y);
        this.label = label;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        LabeledPoint that = (LabeledPoint) o;
        return Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), label);
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString.substring(0, superString.length() -1)
            + ", label='" + label + '\'' +
            '}';
    }
}
