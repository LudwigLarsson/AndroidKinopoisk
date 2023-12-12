package ru.nitestalker.androidkinopoisk.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import ru.nitestalker.androidkinopoisk.model.docs.Movie;

@Data
public class MovieResponse {

    @SerializedName("docs") // Поскольку с сервера прилетает объект docs, а не movies
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
