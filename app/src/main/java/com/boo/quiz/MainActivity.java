package com.boo.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnStart,btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart=findViewById(R.id.btnStart);
        btnShare=findViewById(R.id.btninvite);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CategoryIntent=new Intent(MainActivity.this,categoryActivity.class);
                startActivity(CategoryIntent);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        String body="Quedy is a quiz and Test Taking App. It has a Huge Collection of Questoions. #Boo. Try It Now.\nabhi dali ni playstore pr baad m kr li share ";

                        Intent ShareIntent=new Intent(Intent.ACTION_SEND);
                        ShareIntent.setType("plain/text");
                        ShareIntent.putExtra(Intent.EXTRA_SUBJECT,"Quedy");
                        ShareIntent.putExtra(Intent.EXTRA_TEXT,body);
                        startActivity(Intent.createChooser(ShareIntent,"Share With"));

            }
        });
    }
}
