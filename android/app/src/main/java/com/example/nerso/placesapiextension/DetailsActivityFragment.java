package com.example.nerso.placesapiextension;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nerso.MyRest.MyRestTask;
import com.example.nerso.MyRest.OnRestTaskListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    private static final String TAG = "DetailsActivityFragment";
    private static final String GOOGLE_PLACES_KEY = "AIzaSyB5XH85F0rRFniXvz4683Gj6Cs6iCMZPQg";

    TextView txtNome;
    TextView txtSensei;
    TextView txtTempoDeTreino;
    TextView txtMensalidade;
    TextView txtAddress;
    TextView txtTelephone;
    TextView txtWebsite;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_details, container, false);

        String nome = getActivity().getIntent().getExtras().getString("nome");
        String sensei = getActivity().getIntent().getExtras().getString("sensei");
        int tempoDeTreino = getActivity().getIntent().getExtras().getInt("tempoDoTreino");
        float mensalidade = getActivity().getIntent().getExtras().getFloat("mensalidade");

        txtNome = (TextView)v.findViewById(R.id.txtNome);
        txtSensei = (TextView)v.findViewById(R.id.txtSensei);
        txtTempoDeTreino = (TextView)v.findViewById(R.id.txtTempoDeTreino);
        txtMensalidade = (TextView)v.findViewById(R.id.txtMensalidade);
        txtAddress = (TextView)v.findViewById(R.id.txtAddress);
        txtTelephone = (TextView)v.findViewById(R.id.txtTelephone);
        txtWebsite = (TextView)v.findViewById(R.id.txtWebsite);


        txtNome.setText(nome);
        txtSensei.setText("Sensei: " + sensei);
        txtTempoDeTreino.setText("Tempo de treino: " + tempoDeTreino);
        txtMensalidade.setText("Mensalidade: " + mensalidade);

        placesAPISearchRequest(nome);

        return v;

    }

    private void placesAPISearchRequest(String name) {

        String urlEncodedNome = "";

        try {
            urlEncodedNome = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?location=-22.9539267,-43.1763985&radius=10000&key=" + GOOGLE_PLACES_KEY + "&query=" + urlEncodedNome;

        MyRestTask myRestTask = new MyRestTask(getActivity(), new OnRestTaskListener() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject json = new JSONObject(result);
                    String status = json.getString("status");

                    if (status.equalsIgnoreCase("OK")) {

                        Log.d(TAG, "PLACES API SEARCH OK");

                        JSONArray data = json.getJSONArray("results");
                        JSONObject res = new JSONObject(data.getString(0));

                        String placeId = res.getString("place_id");

                        placesAPIDetailsRequest(placeId);

                    } else {
                        Log.e(TAG,"PLACES API SEARCH FAILED");
                    }

                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }

            }

            @Override
            public void onError(int responseCode, String message) {

            }
        });
        myRestTask.execute(url, "");

    }

    private void placesAPIDetailsRequest(String placeId) {

        String url = "https://maps.googleapis.com/maps/api/place/details/json?key=" + GOOGLE_PLACES_KEY + "&placeid=" + placeId;

        MyRestTask req = new MyRestTask(getActivity(), new OnRestTaskListener() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject json = new JSONObject(result);
                    String status = json.getString("status");

                    if (status.equalsIgnoreCase("OK")) {

                        Log.d(TAG, "PLACES API DETAILS OK");

                        JSONObject data = json.getJSONObject("result");

                        String address = data.getString("formatted_address");
                        String phone = data.getString("international_phone_number");
                        String website = data.getString("website");

                        txtAddress.setText("Endereço: " + address);
                        txtTelephone.setText("Telefone: " + phone);
                        txtWebsite.setText("Website: " + website);

                    } else {
                        Log.e(TAG,"PLACES API DETAILS FAILED");
                    }

                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }

            }

            @Override
            public void onError(int responseCode, String message) {

            }
        });
        req.setProgressBarEnabled(true);
        req.execute(url, "");

    }

}
