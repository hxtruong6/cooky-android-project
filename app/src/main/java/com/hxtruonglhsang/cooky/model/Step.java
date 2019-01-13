package com.hxtruonglhsang.cooky.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Step {
    private int stepNumber;
    private int foodId;
    private String description;
    private List<String> images;

    public Step() {

    }

    public Step(int stepNumber, int foodId, String description, List<String> images) {
        this.stepNumber = stepNumber;
        this.foodId = foodId;
        this.description = description;
        this.images = images;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("description", description);
        result.put("images", images);
        return result;
    }
}
