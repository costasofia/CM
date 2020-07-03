package com.example.cm;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Infor extends AppCompatActivity {
    TextView tvAssunto, tvLocal;
    String s_Assunto, s_Local, s_Id;
    ImageView btnBack;

    DBOpenHelper dbOpenHelper;
    int getId;
    Button btnUpdate;
    Button btnDelete;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);

        dbOpenHelper = new DBOpenHelper(this);


        tvAssunto = (TextView) findViewById(R.id.tvAssunto_Infor);
        tvLocal = (TextView) findViewById(R.id.tvLocal_Infor);

        Intent intent = getIntent();
        if (intent != null) {
            s_Id = intent.getStringExtra("id");
            s_Assunto = intent.getStringExtra("assunto");
            s_Local = intent.getStringExtra("local");
        }
        getId = Integer.parseInt(s_Id);

        tvAssunto.setText(s_Assunto);
        tvLocal.setText(s_Local);


        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Infor.this, ShowAll.class);
                intent.putExtra("id", s_Id);
                startActivity(intent);
            }
        });

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Infor.this, UpdateActivity.class);
                intent.putExtra("id", s_Id);
                intent.putExtra("assunto", s_Assunto);
                intent.putExtra("local", s_Local);
                startActivity(intent);
            }
        });

        btnDelete = findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Infor.this, ShowAll.class);
                intent.putExtra("id", s_Id);
                dbOpenHelper.deleteData(s_Id);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.NotaApagada), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

}

