package fr.omayma.marsrobot.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sol implements Serializable {
    private String solId;
    private double avgTemp;
    private double minTemp;
    private double maxTemp;
    private double avgPressure;
    private double minPressure;
    private double maxPressure;
    private List<WindSensor> windSensors;

    public Sol(String solId, JSONObject json) {
        this.solId = solId;
        this.fromJSONObject(json);
    }

    private void fromJSONObject(JSONObject json) {
        try {
            JSONObject temperatureData = json.optJSONObject("AT");
            if (temperatureData != null) {
                this.avgTemp = temperatureData.optDouble("av", 0);
                this.minTemp = temperatureData.optDouble("mn", 0);
                this.maxTemp = temperatureData.optDouble("mx", 0);
            }

            JSONObject pressureData = json.optJSONObject("PRE");
            if (pressureData != null) {
                this.avgPressure = pressureData.optDouble("av", 0);
                this.minPressure = pressureData.optDouble("mn", 0);
                this.maxPressure = pressureData.optDouble("mx", 0);
            }

            this.windSensors = new ArrayList<>();
            JSONObject windData = json.optJSONObject("WD");

            for (int i = 0; i <= 15; i++) {
                String directionKey = String.valueOf(i);
                WindSensor windSensor;

                if (windData != null && windData.has(directionKey)) {
                    JSONObject windInfo = windData.getJSONObject(directionKey);
                    String sensorId = directionKey;
                    double windSpeed = windInfo.optDouble("ct", 0);
                    double directionDegrees = windInfo.optDouble("compass_degrees", 0);
                    windSensor = new WindSensor(sensorId, windSpeed, directionDegrees);
                } else {
                    windSensor = new WindSensor(String.valueOf(i), 0, 0);
                }

                this.windSensors.add(windSensor);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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