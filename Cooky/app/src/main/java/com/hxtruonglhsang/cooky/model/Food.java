package com.hxtruonglhsang.cooky.model;

import java.util.List;

public class Food {
    private int id;
    private String name;
    private String userId; //username
    private List<String> likes; //username
    private List<Resource> resources;
    private List<String> imgs;      //finished
    private List<Step> steps;
}
