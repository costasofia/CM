package com.example.cm;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    private int IdPonto;
    RecyclerAdapter adapter;
    RecyclerView rv;
    List<Ponto> pontos = new ArrayList<>();
    private String IdUtilizador;
    ImageView btnBack;
    EditText txtDescricao, txtTema;
    String Tema, Descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        Bundle ibundle = getIntent().getExtras();
        IdUtilizador = ibundle.getString("IdUtilizador");
        IdPonto = ibundle.getInt("IdPonto");
        Tema = ibundle.getString("Tema");
        Descricao = ibundle.getString("Descricao");
        getPontos();

        RecyclerView rv = findViewById(R.id.pontosList);
        adapter = new RecyclerAdapter(pontos, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        //Volta para o mapa
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("IdUtilizador", IdUtilizador);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    //Vai para o detalhe do ponto
    @Override
    public void onNoteClick(int position) {
        pontos.get(position);
        Intent intent = new Intent(this, PontosActivity.class);
        Bundle mbudle = new Bundle();
        mbudle.putString("IdUtilizador", IdUtilizador);
        IdPonto = pontos.get(position).getIdPonto();
        Tema = pontos.get(position).getTema();
        Descricao = pontos.get(position).getDescricao();
        mbudle.putInt("IdPonto", IdPonto);
        mbudle.putString("Tema", Tema);
        mbudle.putString("Descricao", Descricao);
        intent.putExtras(mbudle);
        startActivity(intent);

    }

    //Vai buscar todos os pontos de um determinado id (Utilizador)
    private void getPontos() {
        String URL = VolleySingleton.URL + "ponto/pontos/" + IdUtilizador;

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    Log.d("Erro", String.valueOf(pontos.size()));

                    try {
                        JSONObject object = response.getJSONObject(i);
                        Ponto ponto = new Ponto(object.getInt("IdPonto"),
                                object.getString("Tema"),
                                object.getString("Descricao"),
                                object.getDouble("Longitude"),
                                object.getDouble("Latitude"),
                                object.getString("Imagem"),
                                object.getInt("IdUtilizador"));
                        adapter.getPonto().add((Ponto) ponto);
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.ErroWS), Toast.LENGTH_SHORT).show();
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

}





