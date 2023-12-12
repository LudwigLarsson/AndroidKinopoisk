package ru.nitestalker.androidkinopoisk.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.nitestalker.androidkinopoisk.db.MovieDatabase;
import ru.nitestalker.androidkinopoisk.db.dao.MovieDao;
import ru.nitestalker.androidkinopoisk.model.docs.Movie;

public class FavouritesViewModel extends AndroidViewModel {

    private final String TAG = "FavouritesViewModel";

    private final MovieDao dao;


    public FavouritesViewModel(@NonNull Application application) {
        super(application);
        dao = MovieDatabase.getInstance(application).movieDao();
    }

    public LiveData<List<Movie>> getFavMovies() {
        return dao.getAllFavoutiteMovies();
    }
}
