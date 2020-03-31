package com.example.cm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Infor extends AppCompatActivity {
    TextView tvAssunto, tvLocal;
    String s_Assunto, s_Local, s_Id;

    DBOpenHelper dbOpenHelper;
    int getId;
    Button btnUpdate, btnDelete;

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


        btnUpdate = (Button) findViewById(R.id.btnUpdate);
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

        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = deleteAndConfirm();
                alertDialog.show();
            }
        });
    }

    private AlertDialog deleteAndConfirm() {

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.mensagem2))
                .setMessage(getResources().getString(R.string.mensagem3))
                .setPositiveButton(getResources().getString(R.string.mensagem2), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbOpenHelper.deleteData(getId);
                        finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.mensagem4), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return alertDialog;


    }

}
