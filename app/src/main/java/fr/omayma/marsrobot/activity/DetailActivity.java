package fr.omayma.marsrobot.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.omayma.marsrobot.R;
import fr.omayma.marsrobot.model.Sol;

public class DetailActivity extends AppCompatActivity {
    private TextView solIdDetailTextView, solTempDetailTextView, solPressureDetailTextView;

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

        // Retrieve Sol object from the Intent
        Sol sol = (Sol) getIntent().getSerializableExtra("sol");

        // Display Sol details
        if (sol != null) {
            displaySolDetails(sol);
        }
    }

    private void displaySolDetails(Sol sol) {
        // Set Sol ID
        solIdDetailTextView.setText("Sol ID: (" + sol.getSolId() + ")");

        // Format the temperature data
        String temperatureText = "Temperature: avg: " + sol.getAvgTemp() + ", min: " + sol.getMinTemp() + ", max: " + sol.getMaxTemp();
        solTempDetailTextView.setText(temperatureText);

        // Format the pressure data
        String pressureText = "Pressure: avg: " + sol.getAvgPressure() + " , min: " + sol.getMinPressure() + " , max: " + sol.getMaxPressure();
        solPressureDetailTextView.setText(pressureText);
    }
}