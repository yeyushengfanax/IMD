package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Anime anime);

    @Query("DELETE FROM anime_table")
    void deleteAll();

    @Query("SELECT * from anime_table ORDER BY title ASC")
    LiveData<List<Anime>> getAllAnime();

    @Query("SELECT * from anime_table LIMIT 1")
    Anime[] getAnyWord();

    @Delete
    void deleteWord(Anime anime);

    @Update
    void update(Anime... anime);
}
