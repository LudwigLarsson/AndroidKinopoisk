package ru.nitestalker.androidkinopoisk.db;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.nitestalker.androidkinopoisk.db.dao.MovieDao;
import ru.nitestalker.androidkinopoisk.model.docs.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase instance = null;
    private static final String DB_NAME = "movie_db";

    public static MovieDatabase getInstance(Application application) {
        if (instance == null)
            instance = Room.databaseBuilder(application, MovieDatabase.class, DB_NAME).build();
        return instance;
    }

    public abstract MovieDao movieDao();
}
