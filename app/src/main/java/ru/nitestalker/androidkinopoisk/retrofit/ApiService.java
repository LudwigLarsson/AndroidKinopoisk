package ru.nitestalker.androidkinopoisk.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.nitestalker.androidkinopoisk.model.MovieResponse;
import ru.nitestalker.androidkinopoisk.model.ReviewResponse;
import ru.nitestalker.androidkinopoisk.model.TrailersResponse;

public interface ApiService {

    @GET("movie?field=rating.kp&search=4-10&sortField=votes.imdb&sortType=-1&token=ZBG4X4G-5MDMH3W-P8RRE07-YZ0JJ10&limit=50")
    Single<MovieResponse> loadMovies(@Query("page") int page); // @Query - для динамичных запросов

    @GET("movie?token=ZBG4X4G-5MDMH3W-P8RRE07-YZ0JJ10&field=id")
    Single<TrailersResponse> loadTrailers(@Query("search") int id);

    @GET("review?token=ZBG4X4G-5MDMH3W-P8RRE07-YZ0JJ10&limit=10&sortField=date&sortType=-1&field=movieId")
    Single<ReviewResponse> loadReviews(@Query("search") int id, @Query("page") int page);

}
