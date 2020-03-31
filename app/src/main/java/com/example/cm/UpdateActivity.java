package com.example.cm;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    EditText edAssunto, edLocal;
    Button btnUpdate;
    String s_Assunto, s_Local, s_Id;
    int getId;
    DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbOpenHelper = new DBOpenHelper(this);

        edAssunto = (EditText) findViewById(R.id.edAssunto_Update);
        edLocal = (EditText) findViewById(R.id.edLocal_Update);

        Intent intent = getIntent();

        s_Id = intent.getStringExtra("id");
        s_Assunto = intent.getStringExtra("assunto");
        s_Local = intent.getStringExtra("local");

        getId = Integer.parseInt(s_Id);
        edAssunto.setText(s_Assunto);
        edLocal.setText(s_Local);

        btnUpdate = (Button) findViewById(R.id.btnUpdate_Update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dbOpenHelper.updateData(getId, edAssunto.getText().toString(), edLocal.getText().toString());
                finish();
                Intent intent = new Intent(UpdateActivity.this, ShowAll.class);
                startActivity(intent);

            }
        });

    }
}
