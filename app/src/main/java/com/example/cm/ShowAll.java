package com.example.cm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowAll extends AppCompatActivity {
    ListView lvShow;
    CustomAdapter customAdapter;
    ArrayList<InformModel> arrayList;
    DBOpenHelper dbOpenHelper;
    InformModel im;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        lvShow = (ListView) findViewById(R.id.lvShow);
        dbOpenHelper = new DBOpenHelper(this);
        arrayList = new ArrayList<>();
        arrayList = dbOpenHelper.getALLInformData();

        customAdapter = new CustomAdapter(this, arrayList);

        lvShow.setAdapter(customAdapter);

        lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                im = customAdapter.getItem(position);
                Intent intent = new Intent(ShowAll.this, Infor.class);
                intent.putExtra("id", im.getId());
                intent.putExtra("assunto", im.getAssunto());
                intent.putExtra("local", im.getLocal());

                startActivity(intent);
            }
        });
    }
}
