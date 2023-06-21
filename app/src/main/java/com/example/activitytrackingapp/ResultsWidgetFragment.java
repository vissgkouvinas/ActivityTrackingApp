package com.example.activitytrackingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ResultsWidgetFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_results_widget, container, false);
        TextView timeText = view.findViewById(R.id.timeText);
        TextView distanceText = view.findViewById(R.id.distanceText);
        TextView elevationText = view.findViewById(R.id.elevationText);
        TextView dateText = view.findViewById((R.id.dateText));
        TextView phraseDateText = view.findViewById((R.id.phraseDateText));

        Codebase.Result result = (Codebase.Result)  (getArguments().getSerializable("result"));
        float time = result.getTime();
        float distance = result.getDistance();
        float elevation = result.getElevation();
        dateText.setText(result.getDate());
        phraseDateText.setText("Your "+result.getTimeframe()+" Run");

        int mins = ((int)time) / 60;
        int secs = ((int)time) % 60;
        timeText.setText( (String.format("%d'%d\"",mins,secs)) );

        distance /= 1000;
        distanceText.setText( (String.format("%.1fkm",distance)) );

        elevationText.setText( (String.format("%.0fm",elevation)) );


        // Inflate the layout for this fragment
        return view;
    }
}