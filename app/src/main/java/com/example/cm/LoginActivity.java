package com.example.cm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText editEmail, editPassword;
    String IdUtilizador;
    Button btnLogin;
    TextView btnRegistar;
    // String URL = "http://192.168.1.67:5000/utilizador/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        editEmail = (EditText) findViewById(R.id.edtEmail);
        editPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistar = (TextView) findViewById(R.id.registar);

        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editPassword.getText().toString().equals("") && editEmail.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Insira uma password e um email", Toast.LENGTH_SHORT).show();
                } else if (editPassword.getText().toString().equals("") && editEmail.getText().toString() != ("")) {
                    Toast.makeText(getApplicationContext(), "Insira uma password", Toast.LENGTH_SHORT).show();
                } else if (editEmail.getText().toString().equals("") && editPassword.getText().toString() != ("")) {
                    Toast.makeText(getApplicationContext(), "Insira um email", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });
    }

    private void login() {
        String URL = VolleySingleton.URL + "utilizador/login";
        StringRequest stringResquest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JWT jwt = new JWT(response);
                    Claim subscriptionMetaData = jwt.getClaim("Email");
                    Claim data = jwt.getClaim("IdUtilizador");
                    String IdUtilizador = data.asString();
                    String parsedValue = subscriptionMetaData.asString();

                    if (editEmail.getText().toString().equals(parsedValue)) {
                        Intent Intent = new Intent(LoginActivity.this, MapsActivity.class);
                        Bundle params = new Bundle();
                        params.putString("IdUtilizador", IdUtilizador);
                        Intent.putExtras(params);
                        startActivity(Intent);
                        Toast.makeText(getApplicationContext(), "Sessão iniciada com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "O Email não existe", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Password incorreta", Toast.LENGTH_SHORT).show();
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
                String password = editPassword.getText().toString().trim();
                param.put("Password", password);
                param.put("Email", editEmail.getText().toString().trim());


                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringResquest);
    }


}