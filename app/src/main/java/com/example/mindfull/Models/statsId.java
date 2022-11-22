package com.example.mindfull.Models;

import com.google.firebase.database.Exclude;

import org.checkerframework.checker.nullness.qual.NonNull;

public class statsId {

    @Exclude
    public String statsId;

    public <T extends statsId> T withId(@NonNull final String id) {
        this.statsId = id;
        return (T)this;
    }

}
