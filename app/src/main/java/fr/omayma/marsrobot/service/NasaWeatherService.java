package fr.omayma.marsrobot.service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.omayma.marsrobot.model.Sol;
import fr.omayma.marsrobot.model.WindSensor;

public class NasaWeatherService {

    private static final String NASA_WEATHER_URL = "https://api.nasa.gov/insight_weather/?api_key=ORD73Qu0022iTH7yAxYGDPqP3UT70gSQcdzuLFVR&feedtype=json&ver=1.0";
    private static NasaWeatherService INSTANCE;

    private RequestQueue requestQueue;
    private Context context;

    private NasaWeatherService(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.context = context;
    }

    public synchronized static NasaWeatherService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NasaWeatherService(context);
        }
        return INSTANCE;
    }

    public void getWeatherData(NasaWeatherCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                NASA_WEATHER_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Sol> sols = new ArrayList<>();
                        try {
                            JSONArray solKeys = response.getJSONArray("sol_keys");
                            for (int i = 0; i < solKeys.length(); i++) {
                                String solId = solKeys.getString(i);
                                JSONObject solData = response.getJSONObject(solId);
                                Sol sol = new Sol(solId, solData);
                                sols.add(sol);
                            }
                            callback.onSuccess(sols);
                        } catch (JSONException e) {
                            callback.onError(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );

        jsonObjectRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        30 * 1000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );

        this.requestQueue.add(jsonObjectRequest);
    }
}
