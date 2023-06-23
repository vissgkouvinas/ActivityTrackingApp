package com.example.activitytrackingapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;


public class StatsWidgetFragment extends Fragment {

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats_widget, container, false);
        TextView firstText = view.findViewById(R.id.firstText);
        TextView secondText = view.findViewById(R.id.secondText);
        TextView percentageLabel = view.findViewById(R.id.percentageLabel);

        float myStat = (float)  (getArguments().getSerializable("myData"));
        float allStat = (float)  (getArguments().getSerializable("generalData"));

        Log.v("StatsFragment", String.valueOf(getArguments().getInt("mode")));
        switch(getArguments().getInt("mode")) {

            case 0:
                int mins = ((int)myStat) / 60;
                int secs = ((int)myStat) % 60;
                firstText.setText( (String.format("%d'%d\"",mins,secs)) );

                mins = ((int)allStat) / 60;
                secs = ((int)allStat) % 60;
                secondText.setText( (String.format("%d'%d\"",mins,secs)) );

                //Showcase percentage
                if(myStat>allStat) {
                    percentageLabel.setText(String.format("You ran %.0f%% more than average.",((myStat-allStat)*100.0f)/myStat));
                } else if (myStat<allStat) {
                    percentageLabel.setText(String.format("You ran %.0f%% less than average.",((allStat-myStat)*100.0f)/allStat));
                }else {
                    percentageLabel.setText("You are average.");
                }
                break;
            case 1:
                myStat /= 1000;
                firstText.setText( (String.format("%.1fkm",myStat)) );

                allStat /= 1000;
                secondText.setText( (String.format("%.1fkm",allStat)) );
                //Showcase percentage
                if(myStat>allStat) {
                    percentageLabel.setText(String.format("You ran %.0f%% further than average.",((myStat-allStat)*100.0f)/myStat));
                } else if (myStat<allStat) {
                    percentageLabel.setText(String.format("You ran %.0f%% shorter than average.",((allStat-myStat)*100.0f)/allStat));
                }else {
                    percentageLabel.setText("You are average.");
                }
                break;
            case 2:
                firstText.setText( (String.format("%.0fm",myStat)) );

                secondText.setText( (String.format("%.0fm",allStat)) );
                //Showcase percentage
                if(myStat>allStat) {
                    percentageLabel.setText(String.format("You climbed %.0f%% more than average.",((myStat-allStat)*100.0f)/myStat));
                } else if (myStat<allStat) {
                    percentageLabel.setText(String.format("You climbed %.0f%% less than average.",((allStat-myStat)*100.0f)/allStat));
                }else {
                    percentageLabel.setText("You are average.");
                }
                break;
        }


        // initializing variable for bar chart.
        barChart = view.findViewById(R.id.idBarChart);

        ArrayList<BarEntry> barEntriesArrayList = new ArrayList<>();
        barEntriesArrayList.add(new BarEntry(1f, myStat));
        barEntriesArrayList.add(new BarEntry(2f, allStat));


        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        int[] graphColors = new int[]{Color.rgb(255, 194, 102), Color.rgb(166, 120, 51)};
        barDataSet.setColors(graphColors);

        // setting text color to transparent
        barDataSet.setValueTextColor(Color.argb(0.0f,0.0f,0.0f,0.0f));

        // setting text size and disabling description
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        //make chart non interactive
        barChart.setTouchEnabled(false);
        barChart.setHighlightPerDragEnabled(false);
        barChart.setHighlightPerTapEnabled(false);

        //Disable all axes and set y axis minimum to 0
        YAxis yaxis = barChart.getAxisLeft();
        yaxis.setEnabled(false);
        yaxis.setDrawGridLines(false);
        yaxis.setAxisMinimum(0);
        YAxis yaxis2 = barChart.getAxisRight();
        yaxis2.setEnabled(false);
        XAxis xaxis = barChart.getXAxis();
        xaxis.setEnabled(false);
        xaxis.setDrawGridLines(false);

        // Inflate the layout for this fragment
        return view;
    }
}

