package fr.omayma.marsrobot.model;

public class WindSensor {
    private String sensorId;
    private double windSpeed;
    private double directionDegrees;

    public WindSensor(String sensorId, double windSpeed, double directionDegrees) {
        this.sensorId = sensorId;
        this.windSpeed = windSpeed;
        this.directionDegrees = directionDegrees;
    }

    public String getSensorId() {
        return sensorId;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getDirectionDegrees() {
        return directionDegrees;
    }
}
