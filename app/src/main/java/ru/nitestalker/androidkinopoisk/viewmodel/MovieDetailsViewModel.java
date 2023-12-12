package ru.nitestalker.androidkinopoisk.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;
import ru.nitestalker.androidkinopoisk.db.MovieDatabase;
import ru.nitestalker.androidkinopoisk.db.dao.MovieDao;
import ru.nitestalker.androidkinopoisk.model.TrailersResponse;
import ru.nitestalker.androidkinopoisk.model.docs.Movie;
import ru.nitestalker.androidkinopoisk.model.json.Trailer;
import ru.nitestalker.androidkinopoisk.retrofit.ApiFactory;

public class MovieDetailsViewModel extends AndroidViewModel {

    private final String TAG = "MovieDetailsViewModel";

    @Getter
    private MutableLiveData<List<Trailer>> listTrailers = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MovieDao dao;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);

        dao = MovieDatabase.getInstance(application).movieDao();
    }

    public LiveData<Movie> getFavouriteMovie(int movieId) {
        return dao.getFavouriteMovie(movieId);
    }

    public void loadTrailers(int id) {
        Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TrailersResponse, List<Trailer>>() { // Преобразуем TrailerResponse в List<Trailer>. Грубо говоря, распаковываем респонс и передаём далее по цепочке список из респонса
                    @Override
                    public List<Trailer> apply(TrailersResponse trailersResponse) throws Throwable {
                        return trailersResponse.getTrailersList().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailers) throws Throwable {
                        listTrailers.setValue(trailers);
                        Log.d(TAG, trailers.toString());
                    }
                }, throwable -> {
                    Log.e(TAG, throwable.toString());
                });
        compositeDisposable.add(disposable);
    }

    public void insertFavMovie(Movie movie) {
        Disposable disposable = dao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void removeFavMovie(int movieId) {
        Disposable disposable = dao.removeMovie(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
