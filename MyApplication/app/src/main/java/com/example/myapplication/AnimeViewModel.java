package com.example.myapplication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AnimeViewModel extends AndroidViewModel {

    private AnimeRepository mRepository;

    private LiveData<List<Anime>> mAllAnime;

    public AnimeViewModel (Application application) {
        super(application);
        mRepository = new AnimeRepository(application);
        mAllAnime = mRepository.getAllAnime();
    }

    LiveData<List<Anime>> getAllAnime() { return mAllAnime; }

    public void insert(Anime anime) { mRepository.insert(anime); }

    //删除所有数据，下次打开应用时初始化，暂无接口
    public void deleteAll() {mRepository.deleteAll();}

    public void deleteAnime(Anime anime) {mRepository.deleteAnime(anime);}

    public void updateAnime(Anime anime) {mRepository.updateAnime(anime);}
}
