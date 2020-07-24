package com.example.practicaltestt.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserInfo {

    @SerializedName("name")
    String userName;
    @SerializedName("image")
    String userProfileImage;
    @SerializedName("items")
    ArrayList<String> imageList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public UserInfo(String userName, String userProfileImage, ArrayList<String> imageList) {
        this.userName = userName;
        this.userProfileImage = userProfileImage;
        this.imageList = imageList;
    }
}
