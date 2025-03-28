package fr.omayma.marsrobot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.List;

import fr.omayma.marsrobot.R;
import fr.omayma.marsrobot.model.Sol;
import fr.omayma.marsrobot.service.NasaWeatherCallback;
import fr.omayma.marsrobot.service.NasaWeatherService;
import fr.omayma.marsrobot.view.adapter.SolAdapter;

public class HomeActivity extends AppCompatActivity {

    private ListView listView;
    private TextView solIdTextView;
    private TextView avgTempTextView;
    private TextView avgPressureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        solIdTextView = findViewById(R.id.sol_id);
        avgTempTextView = findViewById(R.id.avg_temperature);
        avgPressureTextView = findViewById(R.id.avg_pressure);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.sol_list);

        NasaWeatherService.getInstance(this).getWeatherData(new NasaWeatherCallback() {
            @Override
            public void onSuccess(List<Sol> sols) {
                Toast.makeText(HomeActivity.this, "Weather Data Retrieved!", Toast.LENGTH_SHORT).show();
                Toast.makeText(HomeActivity.this, "NB Sols : " + sols.size(), Toast.LENGTH_SHORT).show();

                SolAdapter adapter = new SolAdapter(HomeActivity.this, sols);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener((parent, view, position, id) -> {
                    Sol clickedSol = sols.get(position);
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("sol", clickedSol);
                    startActivity(intent);
                });
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(HomeActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
