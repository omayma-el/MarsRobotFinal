package fr.omayma.marsrobot.service;

import java.util.List;

import fr.omayma.marsrobot.model.Sol;

public interface NasaWeatherCallback {
    void onSuccess(List<Sol> sols);  // Pass the list of sols
    void onError(Exception e);        // Pass the exception
}
