package com.example.cm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edAssunto, edLocal;
    Button btnAdd, btnShowAll, button2;
    DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbOpenHelper = new DBOpenHelper(this);

        edAssunto = (EditText) findViewById(R.id.edAssunto);
        edLocal = (EditText) findViewById(R.id.edLocal);

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
                    dbOpenHelper.insertData(assunto, local);
                    edAssunto.setText("");
                    edLocal.setText("");
                }
            }
        });
        btnShowAll = (Button) findViewById(R.id.btnShowAll);
        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowAll.class);
                startActivity(intent);
            }
        });
    }
}
