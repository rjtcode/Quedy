package com.boo.quiz;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private List<String> Sets;
    private String category;

    public GridAdapter(List<String> Sets,String category){
        this.Sets=Sets;
        this.category=category;
    }


    @Override
    public int getCount() {
        return Sets.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;

        if(convertView ==null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setitem,parent,false);
        }else{
            view =convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questionIntent=new Intent(parent.getContext(),Questions.class);
                questionIntent.putExtra("category",category);
                questionIntent.putExtra("SetId",Sets.get(position));
                        //7  10
                ///////888888


                parent.getContext().startActivity(questionIntent);
            }
        });

        ((TextView)view.findViewById(R.id.textitemset)).setText(String.valueOf(position+1));

        return view;
    }
}
