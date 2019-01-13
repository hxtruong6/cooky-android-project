package com.hxtruonglhsang.cooky.model;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Ingredient {
    private String ingredientId;
    private String name;
    private String type;
    private String description;
    private Float amount;

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAmount() {
        return amount;
    }

    public Ingredient() {

    }

    public Ingredient(String ingredientId, String name, String type, String description, Float amount) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.type = type;
        this.description = description;
        this.amount = amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("type", type);
        result.put("description", description);
        result.put("ingredientId", ingredientId);
        result.put("amount", amount);
        return result;
    }
}
