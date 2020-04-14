package com.example.cm;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cm.Ponto;
import com.example.cm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerActivity extends AppCompatActivity implements RecyclerAdapter.OnNoteListener {
    RecyclerView rv;
    private int IdPonto;
    List<Ponto> pontos = new ArrayList<>();
    private String IdUtilizador;
    EditText txtDescricao, txtTema;
    Button btnUpdate, btnDelete;
    Dialog dialog;
    String Tema, Descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {
                IdUtilizador = params.getString("IdUtilizador");
            }
        }
        //  Bundle ibundle = getIntent().getExtras();
        //    IdUtilizador = ibundle.getString("IdUtilizador");

        getPontos();
        ;

        RecyclerView rv = findViewById(R.id.pontosList);
        RecyclerAdapter adapter = new RecyclerAdapter(pontos, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

    }

    @Override
    public void onNoteClick(int position) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_ponto);
        dialog.show();
        IdPonto = pontos.get(position).getIdPonto();
        Tema = pontos.get(position).getTema();
        Descricao = pontos.get(position).getDescricao();

        txtDescricao = dialog.findViewById(R.id.txtDescricao);
        txtTema = dialog.findViewById(R.id.txtTema);
        btnUpdate = dialog.findViewById(R.id.btnUpdate);
        btnDelete = dialog.findViewById(R.id.btnDelete);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remover();
            }
        });
    }


    private void getPontos() {
        String URL = VolleySingleton.URL + "ponto/pontos/" + IdUtilizador;

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            List<Ponto> pontos = new ArrayList<>();

            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Ponto ponto = new Ponto(object.getInt("IdUtilizador"), object.getString("Tema"), object.getString("Descricao"),
                                object.getDouble("Longitude"), object.getDouble("Latitude"), object.getString("Imagem"),
                                object.getInt("IdUtilizador"));
                        pontos.add(ponto);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Erro na conexão com o WS", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        Log.d("ERRO1", "onErrorResponse1: " + error.getMessage());
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                                Log.d("ERRO2", "onErrorResponse2: " + e1.getMessage());
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                                Log.d("ERRO3", "onErrorResponse3: " + e2.getMessage());
                            }
                        }
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(getRequest);
    }

    private void editar() {
        String URL = VolleySingleton.URL + "ponto/alterarPonto/" + IdPonto;
        final String alterar = "alterado";

        StringRequest postResquest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            if (response.equals(alterar)) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Ponto alterado", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "Ponto não alterado", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Erro na conexão com o WS", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        Log.d("ERRO1", "onErrorResponse1: " + error.getMessage());
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                                Log.d("ERRO2", "onErrorResponse2: " + e1.getMessage());
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                                Log.d("ERRO3", "onErrorResponse3: " + e2.getMessage());
                            }
                        }
                    }
                }) {

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("Tema", txtTema.getText().toString());
                parametros.put("Descricao", txtDescricao.getText().toString());


                return parametros;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(postResquest);
    }

    public void remover() {
        String URL = VolleySingleton.URL + "pontos/apagarPonto/" + IdPonto;
        StringRequest apagarResquest = new StringRequest(Request.Method.DELETE, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String apagar = "Apagado";
                        // Log.d("REGISTO", "onResponse: " + response.length());
                        try {
                            if (response.equals(apagar)) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "O ponto foi apagado", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "O ponto não foi apagado", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Erro na conexão com o WS", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        Log.d("ERRO1", "onErrorResponse1: " + error.getMessage());
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                                Log.d("ERRO2", "onErrorResponse2: " + e1.getMessage());
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                                Log.d("ERRO3", "onErrorResponse3: " + e2.getMessage());
                            }
                        }
                    }
                }) {
        };

        VolleySingleton.getInstance(this).addToRequestQueue(apagarResquest);
    }
}





