package com.example.practicaltestt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.practicaltestt.R;
import com.example.practicaltestt.model.UserInfo;

import java.util.ArrayList;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    private ArrayList<UserInfo> userInfoList;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUserProfile;
        TextView tvUserName;
        RecyclerView rvImages;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.ivUserProfile = itemView.findViewById(R.id.row_userlist_adapter_iv_profile);
            this.tvUserName = itemView.findViewById(R.id.row_userlist_adapter_tv_username);
            this.rvImages = itemView.findViewById(R.id.row_userlist_adapter_rv_images);
        }
    }

    public UserListAdapter(Context context, ArrayList<UserInfo> data) {
        this.userInfoList = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_userlistadapter, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        GridLayoutManager layoutManager = null;
        final ImageListAdapter imageListAdapter = new ImageListAdapter(context, userInfoList.get(position).getImageList());
        if (userInfoList.get(position).getImageList().size() % 2 == 0) {

            layoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int positionSpan) {

                    if (positionSpan == 0) {
                        return 2;
                    }
                    return 1;

                }
            });
        }


        holder.rvImages.setLayoutManager(layoutManager);
        holder.rvImages.setAdapter(imageListAdapter);
        holder.tvUserName.setText(userInfoList.get(position).getUserName());
        Glide.with(context).load(userInfoList.get(position).getUserProfileImage()).error(R.drawable.ic_launcher_background).into(holder.ivUserProfile);

    }

    @Override
    public int getItemCount() {
        return userInfoList.size();
    }
}