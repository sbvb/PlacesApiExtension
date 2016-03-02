package com.example.nerso.placesapiextension;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nerso.MyRest.MyRestTask;
import com.example.nerso.MyRest.OnRestTaskListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = "MainActivityFragment";

    private ArrayList<Estabelecimento> estabelecimentos;
    private RecyclerView recyclerView;
    private EstabelecimentoAdapter estabelecimentoAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        estabelecimentos = new ArrayList<Estabelecimento>();

        return view;
    }

    public void receiveQuery(String s) {

        String req = "http://php-nerso.rhcloud.com/index.php/estabelecimentos/estabelecimentos/nome_academia/" + s;

        final MyRestTask rest = new MyRestTask(getActivity(), new OnRestTaskListener() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onSuccess(String result) {

                Log.d(TAG, "response code: 200");
                Log.d(TAG, result);

                try {
                    JSONObject json = new JSONObject(result);
                    String status = json.getString("status");

                    if (status.equalsIgnoreCase("true")) {

                        estabelecimentos.clear();

                        JSONArray data = json.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject itemObj = new JSONObject(data.getString(i));

                            int id = itemObj.getInt("id");
                            String nome = itemObj.getString("nome_academia");
                            int tempoDoTreino = itemObj.getInt("tempo_do_treino");
                            String sensei = itemObj.getString("sensei");
                            float mensalidade = (float)itemObj.getDouble("mensalidade");

                            Estabelecimento item = new Estabelecimento(id, nome, tempoDoTreino, sensei, mensalidade);

                            estabelecimentos.add(item);


                        }

                        populateRecyclerView();

                    }

                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }

            }

            @Override
            public void onError(int responseCode, String message) {

                Log.d(TAG, "response code: " + responseCode);
                Log.d(TAG, message);

                Toast.makeText(getActivity().getApplicationContext(), "Não encontrou resultados", Toast.LENGTH_SHORT).show();

            }
        });
        rest.setProgressBarEnabled(true);
        rest.execute(req, "");

    }

    private void populateRecyclerView() {

        estabelecimentoAdapter = new EstabelecimentoAdapter(getContext(), estabelecimentos);
        recyclerView.setAdapter(estabelecimentoAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }
}
