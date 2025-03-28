package fr.omayma.marsrobot.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.omayma.marsrobot.R;
import fr.omayma.marsrobot.service.NasaWeatherCallback;
import fr.omayma.marsrobot.service.NasaWeatherService;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NasaWeatherService.getInstance(this).getWeatherData(new NasaWeatherCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(HomeActivity.this, "Weather Data Retrieved!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                Toast.makeText(HomeActivity.this, "Failed to Retrieve Weather Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}