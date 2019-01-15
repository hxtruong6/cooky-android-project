package com.hxtruonglhsang.cooky.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

@IgnoreExtraProperties
public class Food {
    private String id;
    private String name;
    private String userId; //username
    private List<String> likes; //userIds
    private List<Ingredient> ingredients;
    private List<String> images;      //finished
    private List<Step> steps;
    private List<Comment> comments;
    private String description;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Food(String id) {
        this.id = id;
    }

    public Food() {
        this.likes = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.images = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Exclude
    public Map<String, Object> toFoodGeneralInfoMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("images", images);
        result.put("description", description);
        result.put("userName", userName);
        return result;
    }

    @Exclude
    public Map<String, Object> toFoodIngredientsMap() {
        HashMap<String, Object> result = new HashMap<>();
        for (int i =0 ; i<ingredients.size(); i++) {
            result.put(String.valueOf(i), ingredients.get(i).toMap());
        }
        return result;
    }

    @Exclude
    public Map<String, Object> toFoodCommentsMap() {
        HashMap<String, Object> result = new HashMap<>();
        for (int i =0 ; i<comments.size(); i++) {
            result.put(String.valueOf(i), comments.get(i).toMap());
        }
        return result;
    }

    @Exclude
    public Map<String, Object> toFoodStepsMap() {
        HashMap<String, Object> result = new HashMap<>();
        for (int i =0 ; i<steps.size(); i++) {
            result.put(String.valueOf(i), steps.get(i).toMap());
        }
        return result;
    }

    @Exclude
    public Map<String, Object> toFoodLikesMap() {
        HashMap<String, Object> result = new HashMap<>();
        for (int i = 0; i < likes.size(); i++) {
            result.put(likes.get(i), true);
        }
        return result;
    }

}
