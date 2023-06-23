package com.example.activitytrackingapp;


import static android.app.PendingIntent.getActivity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GnssAntennaInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import Client;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Codebase.Result;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    String username;

    float[] statistics = {0.0f,0.0f,0.0f,0.0f};

    float[] generalStatistics= {0.0f,0.0f,0.0f,0.0f};

    public ArrayList<Result> resultsList = new ArrayList<Result>();
    Bundle resultsData = new Bundle();
    Bundle statsData = new Bundle();
    //ResultsFragment resultsFragment = new ResultsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button uploadFileBTN = findViewById(R.id.uploadFileBTN);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);


        uploadFileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    //permission allowed
                    openFile(myUri);
                } else {
                    //permission not allowed
                    requestPermissions();
                }
            }
        });
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        List<Fragment> aliveFragments;
        switch (item.getItemId()) {
            case R.id.home:
                aliveFragments = getSupportFragmentManager().getFragments();
                if(!(aliveFragments.size()==0)) {
                    for (Fragment fragment : aliveFragments) {
                        if (fragment != null) {
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                    }
                }
                return true;

            case R.id.results:
                aliveFragments = getSupportFragmentManager().getFragments();
                if(aliveFragments.size()==0){
                    Log.v("Activity", String.valueOf(getSupportFragmentManager().getFragments().size()));

                    resultsData.putSerializable("results", resultsList);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_container_view, ResultsFragment.class, resultsData)
                            .commit();
                }else {
                    Fragment fragment = aliveFragments.get(getSupportFragmentManager().getFragments().size() - 1);
                    String fragmentType = String.valueOf(fragment);
                    int indexOfBracket = fragmentType.indexOf("{");
                    if (!(   (fragmentType.substring(0, indexOfBracket).equals("ResultsWidgetFragment"))   ||   (fragmentType.substring(0, indexOfBracket).equals("ResultsFragment"))  )) {

                        for (Fragment fragmentToDestroy : aliveFragments ) {
                            if (fragment != null) {
                                getSupportFragmentManager().beginTransaction().remove(fragmentToDestroy).commit();
                            }
                        }

                        Log.v("Activity", fragmentType.substring(0, indexOfBracket));
                        resultsData.putSerializable("results", resultsList);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragment_container_view, ResultsFragment.class, resultsData)
                                .commit();
                    }
                }
                return true;

            case R.id.statistics:

                aliveFragments = getSupportFragmentManager().getFragments();
                if(aliveFragments.size()==0){

                    statsData.putSerializable("statistics", statistics);
                    statsData.putSerializable("generalStatistics", generalStatistics);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_container_view, StatsFragment.class, statsData)
                            .commit();
                }else {
                    Fragment fragment = aliveFragments.get(getSupportFragmentManager().getFragments().size() - 1);
                    String fragmentType = String.valueOf(fragment);
                    int indexOfBracket = fragmentType.indexOf("{");
                    if (!(   (fragmentType.substring(0, indexOfBracket).equals("StatsWidgetFragment"))   ||   (fragmentType.substring(0, indexOfBracket).equals("StatsFragment"))  )) {

                        for (Fragment fragmentToDestroy : aliveFragments ) {
                            if (fragment != null) {
                                getSupportFragmentManager().beginTransaction().remove(fragmentToDestroy).commit();
                            }
                        }

                        Log.v("Activity", fragmentType.substring(0, indexOfBracket));
                        statsData.putSerializable("statistics", statistics);
                        statsData.putSerializable("generalStatistics", generalStatistics);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragment_container_view, StatsFragment.class, statsData)
                                .commit();
                    }
                }
                return true;
        }
        return false;
    }

    Uri myUri = Uri.parse("/STORAGE/SDCARD");

    private void openFile(Uri pickerInitialUri) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/octet-stream");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, 2);
    }

    String pathOfChosenFile;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            Uri currFileURI = data.getData();
            pathOfChosenFile = currFileURI.getPath();
            Toast.makeText(MainActivity.this, pathOfChosenFile, Toast.LENGTH_SHORT).show();
            (new Thread() {
                public void run() {
                    InputStream inputStream;
                    try {
                        inputStream = getContentResolver().openInputStream(currFileURI);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Client client = new Client(inputStream);
                    client.start();
                    try {
                        client.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Result result = client.getResult();
                    resultsList.add(result);
                    username = result.getName();


                    statistics = client.getStatistics();
                    generalStatistics = client.getGeneralStatistics();
                    Log.v("ACTIVITY","THIS IS INSIDE THE ACTIVITY\n----------"+username+"----------\n");
                    /*
                    for (Codebase.Result result : resultsList) {
                        if(result != null) {
                            Log.v("ACTIVITY", result.toString());
                        }
                    }*/
                    if(statistics != null) {
                        Log.v("ACTIVITY", "My Statistics");
                        for (float stat : statistics) {
                            Log.v("ACTIVITY", String.valueOf(stat));
                        }
                    }
                    if(generalStatistics != null) {
                        Log.v("ACTIVITY", "ALL Statistics");
                        for (float stat : generalStatistics) {
                            Log.v("ACTIVITY", String.valueOf(stat));
                        }
                    }
                }
            }).start();

        }
    }

    private boolean checkPermissions() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Απαιτείται άδεια πρόσβασης στα αρχεία της συσκευής!", Toast.LENGTH_SHORT).show();
        } else
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
    }



}