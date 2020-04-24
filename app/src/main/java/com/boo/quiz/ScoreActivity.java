package com.boo.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {
    private TextView Scoreid,total;
    Button doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Scoreid=findViewById(R.id.Scoreid);
        total=findViewById(R.id.total);
        doneBtn=findViewById(R.id.scoreBtn);

        Scoreid.setText(String.valueOf(getIntent().getIntExtra("Score",0)));
        total.setText("OUT OF "+String.valueOf(getIntent().getIntExtra("total",0)));

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
