package com.example.gitapp.model;

import com.google.gson.annotations.SerializedName;

public class GitRepos {
    public int id;
    public String name;
    public int size;
    public String language;
    @SerializedName("avatar_url")
    public String avatarUrl;
}
