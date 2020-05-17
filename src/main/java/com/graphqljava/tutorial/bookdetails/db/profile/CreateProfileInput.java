package com.graphqljava.tutorial.bookdetails.db.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileInput {
    private String name;
    private String email;
    private int age;

    
}