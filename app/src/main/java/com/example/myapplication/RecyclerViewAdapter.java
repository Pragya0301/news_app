package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mNewsTitles = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mNewsTitles) {
        this.mContext = mContext;
        this.mNewsTitles = mNewsTitles;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item,parent,false) ;
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         Log.d("onBindViewHolder","onBindViewHolder: called");

         holder.news.setText(mNewsTitles.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView news;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            news = itemView.findViewById(R.id.news_title);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
