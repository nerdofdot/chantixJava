package com.example.quitsmoking;

public class graphdata {
    String months;
    int value;

    public graphdata(String months, int value) {
        this.months = months;
        this.value = value;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
