package com.example.myapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AnimeRepository {

    private AnimeDao mAnimeDao;
    private LiveData<List<Anime>> mAllAnime;

    AnimeRepository(Application application) {
        AnimeRoomDatabase db = AnimeRoomDatabase.getDatabase(application);
        mAnimeDao = db.animeDao();
        mAllAnime = mAnimeDao.getAllAnime();
    }

    LiveData<List<Anime>> getAllAnime() {
        return mAllAnime;
    }

    public void insert (Anime anime) {
        new insertAsyncTask(mAnimeDao).execute(anime);
    }

    public void deleteAll()  {
        new deleteAllAnimeAsyncTask(mAnimeDao).execute();
    }

    public void deleteAnime(Anime anime)  {
        new deleteAnimeAsyncTask(mAnimeDao).execute(anime);
    }

    public void updateAnime(Anime word)  {
        new updateAnimeAsyncTask(mAnimeDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Anime, Void, Void> {

        private AnimeDao mAsyncTaskDao;

        insertAsyncTask(AnimeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Anime... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllAnimeAsyncTask extends AsyncTask<Void, Void, Void> {
        private AnimeDao mAsyncTaskDao;

        deleteAllAnimeAsyncTask(AnimeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteAnimeAsyncTask extends AsyncTask<Anime, Void, Void> {
        private AnimeDao mAsyncTaskDao;

        deleteAnimeAsyncTask(AnimeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Anime... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }

    private static class updateAnimeAsyncTask extends AsyncTask<Anime, Void, Void> {
        private AnimeDao mAsyncTaskDao;

        updateAnimeAsyncTask(AnimeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Anime... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
