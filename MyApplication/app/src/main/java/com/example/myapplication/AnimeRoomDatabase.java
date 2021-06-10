package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Database(entities = {Anime.class}, version = 1, exportSchema = false)
public abstract class AnimeRoomDatabase extends RoomDatabase {

    public abstract AnimeDao animeDao();
    private static AnimeRoomDatabase INSTANCE;

    static AnimeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AnimeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AnimeRoomDatabase.class, "anime_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AnimeDao mDao;

        DBService dbService = DBService.getDbService();
        List<Anime> db_animes = dbService.getAnimeData();


        PopulateDbAsync(AnimeRoomDatabase db) {
            mDao = db.animeDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            if(mDao.getAnyWord().length < 1) {
                mDao.deleteAll();
                for( int i = 0; i <= db_animes.size() - 1; i++) {
                    mDao.insert(db_animes.get(i));
                }
            }
            return null;
        }
    }
}