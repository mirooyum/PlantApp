package com.example.gardenwiz_v3;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import API.RetrofitBuilder;
import API.SensorApi;
import API.plantApi;
import API.plantData;
import API.resultsData;
import API.runsData;
import API.sensorData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdvQuery extends DialogFragment implements OnItemSelectedListener {
    private Context context;
    private EditText input_name;
    private QuickQuery.OnMyDialogResult listener;
    private Spinner seasonSpin;
    private Spinner typeSpin;
    private Spinner bloomSpin;
    private Spinner droughtSpin;
    private Spinner durationSpin;
    private Spinner stateSpin;
    private Spinner commSpin;
    private Switch edible;
    private sensorData sensors;

    public AdvQuery(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = (inflater.inflate(R.layout.advdialog, null));

        //set edit texts
        input_name = v.findViewById(R.id.name_input);

        //set spinners
        //season
        seasonSpin = (Spinner) v.findViewById(R.id.seasonSpinner);
        ArrayAdapter<CharSequence> seasonAdapter = ArrayAdapter.createFromResource(context,
                R.array.seasonDrop, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        seasonSpin.setAdapter(seasonAdapter);
        seasonSpin.setOnItemSelectedListener(this);

        //type
        typeSpin = (Spinner) v.findViewById(R.id.spinner_type);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(context,
                R.array.typeDrop, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        typeSpin.setAdapter(typeAdapter);
        typeSpin.setOnItemSelectedListener(this);

        //bloom
        bloomSpin = (Spinner) v.findViewById(R.id.bloomSpin);
        ArrayAdapter<CharSequence> bloomAdapter = ArrayAdapter.createFromResource(context,
                R.array.bloomDrop, android.R.layout.simple_spinner_dropdown_item);
        bloomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloomSpin.setAdapter(bloomAdapter);
        bloomSpin.setOnItemSelectedListener(this);

        //drought
        droughtSpin = (Spinner) v.findViewById(R.id.droughtSpin);
        ArrayAdapter<CharSequence> droughtAdapter = ArrayAdapter.createFromResource(context,
                R.array.droughtDrop, android.R.layout.simple_spinner_dropdown_item);
        droughtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        droughtSpin.setAdapter(droughtAdapter);
        droughtSpin.setOnItemSelectedListener(this);

        //duration
        durationSpin = (Spinner) v.findViewById(R.id.durationSpin);
        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(context,
                R.array.durationDrop, android.R.layout.simple_spinner_dropdown_item);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpin.setAdapter(durationAdapter);
        durationSpin.setOnItemSelectedListener(this);

        //state
        stateSpin = (Spinner) v.findViewById(R.id.spinner_state);
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(context,
                R.array.StateDrop, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        stateSpin.setAdapter(stateAdapter);
        stateSpin.setOnItemSelectedListener(this);

        //commercial
        commSpin = (Spinner) v.findViewById(R.id.commSpin);
        ArrayAdapter<CharSequence> commAdapter = ArrayAdapter.createFromResource(context,
                R.array.commDrop, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        commAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        commSpin.setAdapter(commAdapter);
        commSpin.setOnItemSelectedListener(this);
        //set Switches

        edible = (Switch) v.findViewById(R.id.edibleSwitch);


        builder.setTitle("Enter Parameters: ")
                .setPositiveButton(R.string.go, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Confirm choices
                        String nameInput = input_name.getText().toString();
                        String stateInput = stateSpin.getSelectedItem().toString();
                        String seasonInput = seasonSpin.getSelectedItem().toString();
                        String bloomInput = bloomSpin.getSelectedItem().toString();
                        String typeInput = typeSpin.getSelectedItem().toString();
                        String droughtInput = droughtSpin.getSelectedItem().toString();
                        String commInput = commSpin.getSelectedItem().toString();

                        Boolean edi = edible.isChecked();
                        String edibleInput;
                        if (edi == true) {
                            edibleInput = "yes";
                        } else {
                            edibleInput = "no";
                        }

                        Retrofit retrofit = RetrofitBuilder.getInstance();
                        SensorApi mySensorAPI = retrofit.create(SensorApi.class);
                        Call<runsData> IDcall = mySensorAPI.getRunID(1);
                        IDcall.enqueue(new Callback<runsData>() {
                            @Override
                            public void onResponse(Call<runsData> IDcall, Response<runsData> IDresponse) {

                                //retrofit instance for updating a run
                                Retrofit retrofit = RetrofitBuilder.getInstance();
                                SensorApi mySensorAPI = retrofit.create(SensorApi.class);
                                Call<runsData> call = mySensorAPI.updateRun(nameInput, IDresponse.body().getRunID());
                                call.enqueue(new Callback<runsData>() {
                                    @Override
                                    public void onResponse(Call<runsData> call, Response<runsData> response) {
                                        System.out.println("RunID " + IDresponse.body().getRunID());

                                        if (IDresponse.body().getRunID() == 0) {
                                            //Error message saying that there are no sensors datas to test and to go run the pi.
                                        } else {
                                            //Retrofit instance for getting sensor data


                                            Retrofit retrofit = RetrofitBuilder.getInstance();
                                            SensorApi mySensorAPI = retrofit.create(SensorApi.class);
                                            Call<sensorData> call2 = mySensorAPI.getSensorData(IDresponse.body().getRunID());

                                            call2.enqueue(new Callback<sensorData>() {

                                                @Override
                                                public void onResponse(Call<sensorData> call2, Response<sensorData> response2) {
                                                    if (response2.code() != 200) {
                                                        System.out.println("check con");
                                                        //txt.setText("check connection");
                                                        return;
                                                    }
                                                    sensors = response2.body();

                                                    //pull sensor input
                                                    String moisture = String.valueOf(sensors.getMoisture());
                                                    String light = String.valueOf(sensors.getLight());
                                                    String ph = String.valueOf(sensors.getPh());
                                                    String humid = String.valueOf(sensors.getHumidity());
                                                    String temperature = String.valueOf(sensors.getTemp());
                                                    String rain = String.valueOf(sensors.getRain());



                                                    //Retrofit call to get plants that match the sensor data
                                                    Retrofit retrofit = RetrofitBuilder.getInstance();
                                                    plantApi plantSearch = retrofit.create(plantApi.class);
                                                    System.out.println(moisture);
                                                    System.out.println(light);
                                                    System.out.println(ph);
                                                    Call<List<plantData>> plantCall = plantSearch.getadvData(sensors.getTemp(), moisture, light, ph, typeInput, seasonInput, bloomInput, stateInput, commInput, droughtInput, edibleInput);


                                                    plantCall.enqueue(new Callback<List<plantData>>() {
                                                        @Override
                                                        public void onResponse(Call<List<plantData>> call, Response<List<plantData>> response3) {
                                                            //txt.setText(response.body().get(1).getCommonName());
                                                            String[] plantNames = new String[response3.body().size()];
                                                            String[] betyID = new String[response3.body().size()];
                                                            for (int i = 0; i < response3.body().size(); i++) {

                                                                plantNames[i] = response3.body().get(i).getCommonName();
                                                                betyID[i] = String.valueOf(response3.body().get(i).getBetydbspeciesid());
                                                                //System.out.println(response3.body().get(i).getCommonName());
                                                            }

                                                            //call method to display a array of Strings (this is the Results)
                                                            listener.applyTexts(plantNames, IDresponse.body().getRunID(), nameInput, humid, moisture, light, temperature, rain, ph, 0);

                                                            //retrofit call to set the Results in the database to be called later
                                                            for (int i = 0; i < response3.body().size(); i++) {
                                                                Retrofit retrofit = RetrofitBuilder.getInstance();
                                                                SensorApi resultCreate = retrofit.create(SensorApi.class);
                                                                //System.out.println(response2.body().getRunID());

                                                                Call<resultsData> resultCall = resultCreate.createResult(IDresponse.body().getRunID(), betyID[i]);
                                                                resultCall.enqueue(new Callback<resultsData>() {
                                                                    @Override
                                                                    public void onResponse(Call<resultsData> call, Response<resultsData> response) {
                                                                        System.out.println("Results");
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<resultsData> call, Throwable t) {
                                                                    }
                                                                });
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<List<plantData>> call, Throwable t) {
                                                            String[] plantNames = new String[1];
                                                            plantNames[0] = "none";


                                                            listener.applyTexts(plantNames, IDresponse.body().getRunID(), nameInput, humid, moisture, light, temperature, rain, ph, 0);
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onFailure(Call<sensorData> call, Throwable t) {
                                                    System.out.println("sensors failed");
                                                }

                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<runsData> call, Throwable t) {
                                        System.out.println("runsData failed");
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<runsData> call, Throwable t) {
                                System.out.println("runs Data failed");
                            }

                        });
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });


        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (QuickQuery.OnMyDialogResult) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement");
        }

    }

    public interface OnMyDialogResult {
        void applyTexts(String[] plantNames, int runID, String runName, String humidVal,
                        String moistureVal, String lightVal, String tempVal, String rainVal,
                        String phVal, int Duration);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        switch (parent.getId()) {
            case R.id.seasonSpinner:
                String seasonInput = seasonSpin.getItemAtPosition(pos).toString();

                break;
            case R.id.spinner_type:
                String typeInput = typeSpin.getItemAtPosition(pos).toString();

                break;
            case R.id.bloomSpin:
                String bloomInput = bloomSpin.getItemAtPosition(pos).toString();

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        //todo
    }
}