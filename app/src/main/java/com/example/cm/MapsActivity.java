package com.example.cm;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    FloatingActionButton dados;
    String IdUtilizador;
    public double Latitude;
    public double Longitude;
    private GoogleMap mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        IdUtilizador = bundle.getString("IdUtilizador");

        dados = findViewById(R.id.dados);
        dados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, RecyclerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("IdUtilizador", IdUtilizador);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        getPonto();
        placeMarkerOnPosition();

        mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Longitude = latLng.longitude;
                Latitude = latLng.latitude;

                Intent intent = new Intent(MapsActivity.this, MapInformActivity.class);
                //passa a informação
                Bundle mbudle = new Bundle();
                mbudle.putDouble("Longitude", Longitude);
                mbudle.putDouble("Latitude", Latitude);
                mbudle.putString("IdUtilizador", IdUtilizador);
                intent.putExtras(mbudle);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), Latitude + " " + Longitude + "\n" + IdUtilizador, Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    public void placeMarkerOnPosition() {
//// check permissions to access resources /////////
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            return;
        }
    }

    private void getPonto() {
        String URL = VolleySingleton.URL + "ponto/pontoID";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Ponto> pontos = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                Ponto ponto = new Ponto(object.getInt("IdUtilizador"), object.getString("Tema"), object.getString("Descricao"),
                                        object.getDouble("Longitude"), object.getDouble("Latitude"), object.getString("imagem"),
                                        object.getInt("IdUtilizador"));

                                pontos.add(ponto);
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Erro na conexão com o WS", Toast.LENGTH_SHORT).show();
                        }

                        for (Ponto ponto : pontos) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(ponto.getLatitude(), ponto.getLongitude()));
                            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ponto.getLatitude(), ponto.getLongitude()), 10f));
                            //     markerOptions.title(ponto.getTema() + "\n" + ponto.getDescricao());
                            //     mapa.setMyLocationEnabled(true);
                            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                            mapa.addMarker(markerOptions);

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
        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
}