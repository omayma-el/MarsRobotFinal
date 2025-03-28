package fr.omayma.marsrobot.model;

import java.util.List;

public class Sol {
    private String solId;
    private double avgTemp;
    private double minTemp;
    private double maxTemp;
    private double avgPressure;
    private double minPressure;
    private double maxPressure;
    private List<WindSensor> windSensors;

    public Sol(String solId, double avgTemp, double minTemp, double maxTemp,
               double avgPressure, double minPressure, double maxPressure,
               List<WindSensor> windSensors) {
        this.solId = solId;
        this.avgTemp = avgTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.avgPressure = avgPressure;
        this.minPressure = minPressure;
        this.maxPressure = maxPressure;
        this.windSensors = windSensors;
    }

    public String getSolId() {
        return solId;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getAvgPressure() {
        return avgPressure;
    }

    public double getMinPressure() {
        return minPressure;
    }

    public double getMaxPressure() {
        return maxPressure;
    }

    public List<WindSensor> getWindSensors() {
        return windSensors;
    }
}
