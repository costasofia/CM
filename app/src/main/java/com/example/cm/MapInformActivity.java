package com.example.cm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;

public class MapInformActivity extends AppCompatActivity {
    ImageButton buttonimg;
    Button btnCancelar, btnRegista;
    EditText editTema, editDescricao;
    String Tema, Descricao, IdUtilizador;
    Double Latitude, Longitude;
    String lat, lng;
    String encoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_inform);

        //passar a informação
        Bundle pbundle = getIntent().getExtras();
        IdUtilizador = pbundle.getString("IdUtilizador");
        Longitude = pbundle.getDouble("Longitude");
        lng = valueOf(Longitude);
        Latitude = pbundle.getDouble("Latitude");
        lat = valueOf(Latitude);

        btnCancelar = findViewById(R.id.btnCancelar);
        btnRegista = findViewById(R.id.btnRegista);
        buttonimg = findViewById(R.id.buttonimg);
        editTema = (EditText) findViewById(R.id.edtTema);
        editDescricao = (EditText) findViewById(R.id.edtDescricao);

        if (ContextCompat.checkSelfPermission(MapInformActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapInformActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        btnRegista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapInformActivity.this, MapsActivity.class);
                inserir();
                Bundle bundle = new Bundle();
                bundle.putString("IdUtilizador", IdUtilizador);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();


            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapInformActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("IdUtilizador", IdUtilizador);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        buttonimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abrir a camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
    }
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            buttonimg.setImageBitmap(captureImage);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            captureImage.compress(Bitmap.CompressFormat.JPEG, 100, bs);
            byte b[] = bs.toByteArray();
            encoded = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }

    public void inserir() {
        final String criar = "true";
        String URL = VolleySingleton.URL + "ponto/criarPonto";

        StringRequest stringResquest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Inserir Pontos", "onResponse: " + response.length());
                        try {
                            if (response.equals(criar)) {
                                Toast.makeText(getApplicationContext(), "Ponto inserido com sucesso", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "O ponto já existe", Toast.LENGTH_SHORT).show();

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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Tema", editTema.getText().toString().trim());
                params.put("Descricao", editDescricao.getText().toString().trim());
                params.put("Longitude", lng);
                params.put("Latitude", lat);
                params.put("Imagem", encoded);
                params.put("IdUtilizador", IdUtilizador);

                return params;
            }


        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringResquest);

    }

}
