package com.hxtruonglhsang.cooky.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.hxtruonglhsang.cooky.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {
    private String id;
    private String userName;
    private String password;
    private String fullName;
    private ArrayList<String> savedFoods;  // save list foodId in database

    public User() {

    }

    public User(String id, String userName, String password, String fullName) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
    }

    public ArrayList<String> getSavedFoods() {
        return savedFoods;
    }

    public void setSavedFoods(ArrayList<String> savedFoods) {
        this.savedFoods = savedFoods;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Exclude
    public Map<String, Object> toUserInfoMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("fullName", fullName);
        return result;
    }

    @Exclude
    public Map<String, Object> toSavedUserFoodsMap() {
        HashMap<String, Object> result = new HashMap<>();
        for (int i = 0; i < savedFoods.size(); i++) {
            result.put(savedFoods.get(i), true);
        }
        return result;
    }


}
