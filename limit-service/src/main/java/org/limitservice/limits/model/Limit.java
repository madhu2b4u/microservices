package org.limitservice.limits.model;

public class Limit {

    private int minimum;
    private int maximum;

    @Override
    public String toString() {
        return "Limit{" +
                "minimum=" + minimum +
                ", maximum=" + maximum +
                '}';
    }

    public Limit(){

    }

    public Limit(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }


}
