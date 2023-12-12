package ru.nitestalker.androidkinopoisk.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;
import ru.nitestalker.androidkinopoisk.model.ReviewResponse;
import ru.nitestalker.androidkinopoisk.model.reviews.Review;
import ru.nitestalker.androidkinopoisk.retrofit.ApiFactory;

public class ReviewsViewModel extends AndroidViewModel {

    private final String TAG = "ReviewsViewModel";

    @Getter
    private MutableLiveData<List<Review>> listReviews = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Getter
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private int page = 1;

    public ReviewsViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadReviews(int id) {
        Disposable disposable = ApiFactory.apiService.loadReviews(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> isLoading.setValue(true)) // isLoading - true при начале
                .doAfterTerminate(() -> isLoading.setValue(false)) // после завершения isLoading - false
                .map(ReviewResponse::getReviews)
                .subscribe(reviews -> {
                    List<Review> reviewList = listReviews.getValue();
                    if (reviewList == null) listReviews.setValue(reviews);
                    else {
                        reviewList.addAll(reviews);
                        listReviews.setValue(reviewList);
                    }
                    page++;
                }, throwable -> {
                    Log.e(TAG, throwable.toString());
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
