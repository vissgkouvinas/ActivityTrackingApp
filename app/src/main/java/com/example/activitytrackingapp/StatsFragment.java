package com.example.activitytrackingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StatsFragment extends Fragment {


    float[] myStatistics;
    float[] generalStatistics;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        Button backBTN = view.findViewById(R.id.backBTN);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Fragment fragment : getParentFragmentManager().getFragments()) {
                    if (fragment != null) {
                        getParentFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                }
            }
        });
        myStatistics = (float[]) getArguments().getSerializable("statistics");
        generalStatistics = (float[]) getArguments().getSerializable("generalStatistics");

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        for (int i =0;i<3;i++) {
            Bundle widgetData = new Bundle();
            widgetData.putSerializable("myData", myStatistics[i]);
            widgetData.putSerializable("generalData", generalStatistics[i]);
            StatsWidgetFragment newFragment = new StatsWidgetFragment();
            fragmentTransaction.add(R.id.statsContainer, StatsWidgetFragment.class, widgetData);
        }
        fragmentTransaction.commit();








        // Inflate the layout for this fragment
        return view;
    }
}