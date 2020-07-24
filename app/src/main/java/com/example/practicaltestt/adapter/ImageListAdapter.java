package com.example.practicaltestt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.practicaltestt.R;

import java.util.ArrayList;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {

    private ArrayList<String> imageList;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.ivImage = itemView.findViewById(R.id.row_imagelist_adapter_iv);

        }
    }

    public ImageListAdapter(Context context, ArrayList<String> data) {
        this.imageList = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_imagelistadapter, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        Glide.with(context).load(imageList.get(position)).error(R.drawable.ic_launcher_background).into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}