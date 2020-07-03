package com.example.cm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    EditText edAssunto, edLocal;
    Button btnAdd;
    DBOpenHelper dbOpenHelper;
    ImageView btnBack;
    private Toolbar toolbar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        dbOpenHelper = new DBOpenHelper(this);

        edAssunto = (EditText) findViewById(R.id.edAssunto);
        edLocal = (EditText) findViewById(R.id.edLocal);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowAll.class);
                startActivity(intent);
            }
        });


        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String assunto = edAssunto.getText().toString();
                String local = edLocal.getText().toString();

                if (assunto.isEmpty() && local.isEmpty()) {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.mensagem), Toast.LENGTH_SHORT).show();
                    edAssunto.setText("");
                    edLocal.setText("");
                } else {
                    Intent intent = new Intent(MainActivity.this, ShowAll.class);
                    startActivity(intent);
                    dbOpenHelper.insertData(assunto, local);
                    edAssunto.setText("");
                    edLocal.setText("");
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.NotaI), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}