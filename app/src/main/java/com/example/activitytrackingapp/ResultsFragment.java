package com.example.activitytrackingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class ResultsFragment extends Fragment {

    ArrayList<Codebase.Result> resultsList = new ArrayList<Codebase.Result>();

    public ResultsFragment(){
        // required a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_results, container, false);
        Button backBTN = view.findViewById(R.id.backBTN);
        resultsList = (ArrayList<Codebase.Result>) getArguments().getSerializable("results");

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        if(!resultsList.isEmpty()) {
            Log.v("FRAGMENT","I GET DATA INSIDE THE FRAGMENT\n");
            for (Codebase.Result result : resultsList) {
                Log.v("FRAGMENT", Float.toString(result.getTime()));
            }
            for (Codebase.Result result : resultsList) {
                Bundle specificResult = new Bundle();
                specificResult.putSerializable("result", result);
                ResultsWidgetFragment newFragment = new ResultsWidgetFragment();
                fragmentTransaction.add(R.id.resultsContainer, ResultsWidgetFragment.class, specificResult);

            }
            fragmentTransaction.commit();
        }

        // Inflate the layout for this fragment
        return view;
    }





}