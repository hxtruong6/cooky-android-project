package com.hxtruonglhsang.cooky.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Comment {
    private String userId;
    private String userName;
    private String content;
    private ArrayList<String> images;

    public Comment() {
    }

    public Comment(String userId, String userName, String content, ArrayList<String> images) {
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.images = images;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("content", content);
        result.put("userId", userId);
        result.put("userName", userName);
        result.put("images", images);
        return result;
    }
}
