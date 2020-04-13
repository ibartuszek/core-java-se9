package tutorial.ch02.exercise09;

import java.text.MessageFormat;

/**
 * Implement a class Car that models a car traveling along the x-axis,
 * consuming gas as it moves. Provide methods to drive by a given number of
 * miles, to add a given number of gallons to the gas tank, and to get the current
 * distance from the origin and fuel level. Specify the fuel efficiency (in
 * miles/gallons) in the constructor. Should this be an immutable class? Why or
 * why not?
 */
public class Car {

    private double gas;
    private double distance;
    private double fuelEfficiency;

    public Car(final double fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
    }

    public void travelling(final double distance) {
        double gasToTravel = distance / this.fuelEfficiency;
        if (gas >= gasToTravel) {
            this.gas -= gasToTravel;
            this.distance += distance;
        } else {
           throw new NotEnoughGasException(distance);
        }
    }

    public void addGas(final double gas) {
        this.gas += gas;
    }

    public double getGas() {
        return gas;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Car{gas=" + gas + ", distance=" + distance + ", fuelEfficiency=" + fuelEfficiency + '}';
    }

    public static void main(String[] args) {
        Car car = new Car(2.0d);
        car.addGas(10.0d);
        car.travelling(10.0d);
        System.out.println(car);
        try {
            car.travelling(20.0d);
        } catch (NotEnoughGasException e) {
            System.out.println(e);
        }

    }
}

class NotEnoughGasException extends RuntimeException {

    private static final String MESSAGE = "There is not enough gas to go for {0} miles in the car";

    public NotEnoughGasException(final double distance) {
        super(MessageFormat.format(MESSAGE, distance));
    }
}
