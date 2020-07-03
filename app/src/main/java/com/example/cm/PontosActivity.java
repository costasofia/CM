package com.example.cm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PontosActivity extends AppCompatActivity {
    private String IdUtilizador;
    private int IdPonto;
    List<Ponto> pontos = new ArrayList<>();
    EditText txtDescricao, txtTema;
    Button btnDelete, btnUpdate;
    String Tema, Descricao;
    ImageView btnBack;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontos);

        Bundle ibundle = getIntent().getExtras();
        IdUtilizador = ibundle.getString("IdUtilizador");
        Tema = ibundle.getString("Tema");
        Descricao = ibundle.getString("Descricao");
        IdPonto = ibundle.getInt("IdPonto");


        txtDescricao = findViewById(R.id.txtDescricao);
        txtTema = findViewById(R.id.txtTema);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);


        txtDescricao.setText(Descricao);
        txtTema.setText(Tema);

        //    Toast.makeText(getApplicationContext(), IdUtilizador + " " + IdPonto + " ", Toast.LENGTH_SHORT).show();


//Volta para a Lista de Pontos
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PontosActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("IdUtilizador", IdUtilizador);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//Faz update do ponto
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PontosActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("IdUtilizador", IdUtilizador);
                intent.putExtras(bundle);
                editar();
                startActivity(intent);
            }
        });
//Faz delete do ponto
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PontosActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("IdUtilizador", IdUtilizador);
                intent.putExtras(bundle);
                remover();
                startActivity(intent);
            }
        });

    }

    //Remove Ponto
    public void remover() {
        String URL = VolleySingleton.URL + "ponto/apagarPonto/" + IdPonto;
        StringRequest deleteResquest = new StringRequest(Request.Method.DELETE, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String apagar = "apagar";
                        Log.d("Erro", "onResponse: " + response.length());
                        try {
                            if (response.equals(apagar)) {

                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.PontoApagado), Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.PontoNApagado), Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.ErroWS), Toast.LENGTH_SHORT).show();
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

        VolleySingleton.getInstance(this).addToRequestQueue(deleteResquest);
    }

    //Faz update de um ponto
    private void editar() {
        String URL = VolleySingleton.URL + "ponto/update/" + IdPonto;
        final String updated = "updated";

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.equals(updated)) {

                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.PontoAlterado), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.PontoNAlterado), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.ErroWS), Toast.LENGTH_SHORT).show();
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
                Map<String, String> param = new HashMap<>();
                param.put("Tema", txtTema.getText().toString());
                param.put("Descricao", txtDescricao.getText().toString());
                return param;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(postRequest);
    }

}
