package com.example.blog.about.model;

import lombok.Data;

@Data
public class About {
    private String name;
    private String headline;
    private String github;
    private String email;
    private String summary;
    private String skills;      // JSONB → String
    private String experience;  // JSONB → String
    private String projects;    // JSONB → String
}
