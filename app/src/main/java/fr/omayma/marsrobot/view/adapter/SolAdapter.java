package fr.omayma.marsrobot.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.omayma.marsrobot.R;
import fr.omayma.marsrobot.model.Sol;

public class SolAdapter extends ArrayAdapter<Sol> {

    private Context context;
    private List<Sol> sols;

    public SolAdapter(Context context, List<Sol> sols) {
        super(context, 0, sols);
        this.context = context;
        this.sols = sols;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sol_list_item, parent, false);
        }

        Sol sol = sols.get(position);

        TextView solIdTextView = convertView.findViewById(R.id.sol_id);
        TextView avgTempTextView = convertView.findViewById(R.id.avg_temperature);
        TextView avgPressureTextView = convertView.findViewById(R.id.avg_pressure);

        solIdTextView.setText("Sol n° " + sol.getSolId());
        avgTempTextView.setText("Temperature: " + String.format("%.2f", sol.getAvgTemp()) + "°C");
        avgPressureTextView.setText("Pressure: " + String.format("%.2f", sol.getAvgPressure()) + " Pa");

        return convertView;
    }
}

