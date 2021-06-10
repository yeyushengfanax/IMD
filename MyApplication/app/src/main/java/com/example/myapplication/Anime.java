package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "anime_table")
class Anime {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "info")
    private String info;
    @ColumnInfo(name = "imageResource")
    private final int imageResource;

    /**
     * Constructor for the Sport data model.
     *
     * @param title The name if the sport.
     * @param info Information about the sport.
     */
    public Anime(@NonNull String title, String info, int imageResource) {
        this.title = title;
        this.info = info;
        this.imageResource = imageResource;
    }

    /**
     * Gets the title of the sport.
     *
     * @return The title of the sport.
     */
    String getTitle() {
        return title;
    }

    /**
     * Gets the info about the sport.
     *
     * @return The info about the sport.
     */
    String getInfo() {
        return info;
    }

    public int getImageResource() {
        return imageResource;
    }

}