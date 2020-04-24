package com.boo.quiz;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Questions extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private TextView question,no_indicate;
    private FloatingActionButton  Bookmark;
    private LinearLayout option_container;
    private Button share,next;
    private int count=0;
    private List<questionModel> list;
    private int  position=0;
    private int  score=0;
    private String SetId;
    private String category;
    private Dialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        androidx.appcompat.widget.Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        question=findViewById(R.id.question);
        no_indicate=findViewById(R.id.no_indicate);
        Bookmark=findViewById(R.id.floatingActionButton);
        option_container=findViewById(R.id.option_container);
        share=findViewById(R.id.sharebtn);
        next=findViewById(R.id.next_btn);

        SetId=getIntent().getStringExtra("SetId");

        loading=new Dialog(this);
        loading.setContentView(R.layout.loading_dialog);
        loading.getWindow().setBackgroundDrawable(getDrawable(R.drawable.loading_component));
        loading.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loading.setCancelable(false);




        list=new ArrayList<>();
        loading.show();
        myRef.child("Sets").child(SetId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String id=dataSnapshot1.getKey();
                    String question=dataSnapshot1.child("question").getValue().toString();
                    String a=dataSnapshot1.child("optionA").getValue().toString();
                    String b=dataSnapshot1.child("optionB").getValue().toString();
                    String c=dataSnapshot1.child("optionC").getValue().toString();
                    String d=dataSnapshot1.child("optionD").getValue().toString();
                    String correctANS=dataSnapshot1.child("correctANS").getValue().toString();

                    list.add(new questionModel(id,question,a,b,c,d,correctANS,SetId));
                }

                    if (list.size()>0){

                    for (int i=0;i<4;i++){
                        option_container.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                checkAnswer((Button) v);
                            }
                        });
                    }

                    playAni(question,0,list.get(position).getQuestion());
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            next.setEnabled(true );
                            next.setAlpha(0.7f);
                            enableOption(true);
                            position++;
                            if (position==list.size()){
                                //go to score

                                Intent scoreint=new Intent(Questions.this,ScoreActivity.class);
                                scoreint.putExtra("Score",score);
                                scoreint.putExtra("total",list.size());
                                startActivity(scoreint);
                                finish();



                                return;
                            }
                            count=0;
                            playAni(question,0,list.get(position).getQuestion() );
                        }
                    });

                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String body="Question:- "+list.get(position).getQuestion()+"\n"+
                                    "(A) "+list.get(position).getA()+"\n"+
                                    "(B) "+ list.get(position).getB()+"\n"+
                                    "(C) "+list.get(position).getC()+"\n"+
                                    "(D) "+list.get(position).getD();
                            Intent ShareIntent=new Intent(Intent.ACTION_SEND);
                            ShareIntent.setType("plain/text");
                            ShareIntent.putExtra(Intent.EXTRA_SUBJECT,"Quiz Help");
                            ShareIntent.putExtra(Intent.EXTRA_TEXT,body);
                            startActivity(Intent.createChooser(ShareIntent,"Share With"));



                        }
                    });

                }else{
                        finish();
                    Toast.makeText(Questions.this, "no Questions", Toast.LENGTH_SHORT).show();
                }
                    loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Questions.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            loading.dismiss();
            finish();
            }
        });


    }

    private void playAni(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value == 0 && count< 4){
                    String option="";
                    if (count==0){
                        option=list.get(position).getA();
                    }else if (count==1){
                        option=list.get(position).getB();
                    }else if (count==2){
                        option=list.get(position).getC();
                    }else if (count==3){
                        option=list.get(position).getD();

                    }
                    playAni(option_container.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (value==0){
                    try {
                        ((TextView) view).setText(data);
                        no_indicate.setText(position+1+"/"+list.size());

                    }catch (ClassCastException ex){
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);
                    playAni(view,1,data);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    private void checkAnswer(Button selected_option){

        enableOption(false);
        next.setEnabled(true);
        next.setAlpha(1);
        if (selected_option.getText().toString().equals(list.get(position).getAnswer())){
            //corect
            score++;
            selected_option.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4caf50")));
        }else{
            selected_option.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
           Button correctoption=(Button) option_container.findViewWithTag(list.get(position).getAnswer());
            correctoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4caf50")));

        }
    }



    private void enableOption(boolean enable){
        for(int i=0;i<4;i++){
            option_container.getChildAt(i).setEnabled(enable);
            if (enable){

                    option_container.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));

            }
        }
    }
}
