package org.acme;

import java.math.BigDecimal;

public class WeatherStats {
    private String city;
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal mean;

    public WeatherStats(String city, BigDecimal min, BigDecimal max, BigDecimal mean) {
        this.city = city;
        this.min = min;
        this.max = max;
        this.mean = mean;
    }

    public String getCity() {
        return city;
    }

    public BigDecimal getMin() {
        return min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getMean() {
        return mean;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public void setMean(BigDecimal mean) {
        this.mean = mean;
    }
}
