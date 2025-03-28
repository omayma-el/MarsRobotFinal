package fr.omayma.marsrobot.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import fr.omayma.marsrobot.R;
import fr.omayma.marsrobot.model.Sol;
import fr.omayma.marsrobot.model.WindSensor;
import fr.omayma.marsrobot.view.SolView;

public class DetailActivity extends AppCompatActivity {
    private TextView solIdDetailTextView, solTempDetailTextView, solPressureDetailTextView;
    private SolView solView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        solIdDetailTextView = findViewById(R.id.sol_id_detail);
        solTempDetailTextView = findViewById(R.id.sol_temp_detail);
        solPressureDetailTextView = findViewById(R.id.sol_pressure_detail);

        Sol sol = (Sol) getIntent().getSerializableExtra("sol");

        if (sol != null) {
            displaySolDetails(sol);
        }

        solView = findViewById(R.id.solView);
        if (sol != null) {
            List<WindSensor> windSensors = sol.getWindSensors();
            float[] windValues = new float[16];

            for (int i = 0; i < windSensors.size(); i++) {
                WindSensor sensor = windSensors.get(i);
                windValues[i] = (float) sensor.getWindSpeed();
            }

            solView = findViewById(R.id.solView);
            solView.setValues(windValues);
        }

    }

    private void displaySolDetails(Sol sol) {
        solIdDetailTextView.setText("Sol ID: (" + sol.getSolId() + ")");
        String temperatureText = "Temperature: avg: " + sol.getAvgTemp() + ", min: " + sol.getMinTemp() + ", max: " + sol.getMaxTemp();
        solTempDetailTextView.setText(temperatureText);
        String pressureText = "Pressure: avg: " + sol.getAvgPressure() + " , min: " + sol.getMinPressure() + " , max: " + sol.getMaxPressure();
        solPressureDetailTextView.setText(pressureText);
    }
}