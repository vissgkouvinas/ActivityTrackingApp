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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

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
                break;
            case 1:
                myStat /= 1000;
                firstText.setText( (String.format("%.1fkm",myStat)) );

                allStat /= 1000;
                secondText.setText( (String.format("%.1fkm",allStat)) );
                break;
            case 2:
                firstText.setText( (String.format("%.0fm",myStat)) );

                secondText.setText( (String.format("%.0fm",allStat)) );
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
        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        int[] graphColors = new int[]{Color.rgb(255, 194, 102), Color.rgb(166, 120, 51)};
        barDataSet.setColors(graphColors);

        // setting text color.
        barDataSet.setValueTextColor(Color.argb(0.0f,0.0f,0.0f,0.0f));

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        //customizing the chart
        barChart.setTouchEnabled(false);
        barChart.setHighlightPerDragEnabled(false);
        barChart.setHighlightPerTapEnabled(false);


        YAxis yaxis = barChart.getAxisLeft();
        yaxis.setEnabled(false);
        YAxis yaxis2 = barChart.getAxisRight();
        yaxis2.setEnabled(false);
        XAxis xaxis = barChart.getXAxis();
        xaxis.setEnabled(false);


        yaxis.setDrawGridLines(false);
        xaxis.setDrawGridLines(false);

        // Inflate the layout for this fragment
        return view;
    }
}
