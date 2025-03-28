package fr.omayma.marsrobot.service;

import java.util.List;

import fr.omayma.marsrobot.model.Sol;

public interface NasaWeatherCallback {
    void onSuccess(List<Sol> sols);
    void onError(Exception e);
}
