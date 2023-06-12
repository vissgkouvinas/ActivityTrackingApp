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
        TextView widgetTitle = view.findViewById(R.id.widgetTitle);

        Codebase.Result temp = (Codebase.Result)  (getArguments().getSerializable("result"));
        widgetTitle.setText( Float.toString( temp.getTime() ) );

        // Inflate the layout for this fragment
        return view;
    }
}