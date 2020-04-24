package com.boo.quiz;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class categoryActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    
    private RecyclerView recycle;
    public static List<CategoryModel> list;
    private Dialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Categories");

        loading=new Dialog(this);
        loading.setContentView(R.layout.loading_dialog);
        loading.getWindow().setBackgroundDrawable(getDrawable(R.drawable.loading_component));
        loading.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loading.setCancelable(false);

        recycle=findViewById(R.id.recycle);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycle.setLayoutManager(layoutManager);

        list=new ArrayList<>();
        final CategoryAdapter adapter=new CategoryAdapter(list);
        recycle.setAdapter(adapter);

        loading.show();
        myRef.child("categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    List<String> Sets=new ArrayList<>();
                    for (DataSnapshot dataSnapshot2:dataSnapshot1.child("Sets").getChildren()){
                        Sets.add(dataSnapshot2.getKey());

                    }
                    list.add(new CategoryModel(dataSnapshot1.child("name").getValue().toString(),
                            Sets,
                            dataSnapshot1.child("url").getValue().toString(),
                            dataSnapshot1.getKey()
                    ));
                }
                adapter.notifyDataSetChanged();
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(categoryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            loading.dismiss();
            finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
