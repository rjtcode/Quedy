package com.boo.quiz;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class setsActivity extends AppCompatActivity {

    private GridView gridev;
    private List<String> Sets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        androidx.appcompat.widget.Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));


        gridev=findViewById(R.id.Gridv);

        Sets=categoryActivity.list.get(getIntent().getIntExtra("position", 0)).getSets();

        GridAdapter adapter=new GridAdapter(Sets,getIntent().getStringExtra("title"));
        gridev.setAdapter(adapter);

    }
}
