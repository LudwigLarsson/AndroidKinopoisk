package ru.nitestalker.androidkinopoisk.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nitestalker.androidkinopoisk.R;
import ru.nitestalker.androidkinopoisk.adapter.ReviewsAdapter;
import ru.nitestalker.androidkinopoisk.model.docs.Movie;
import ru.nitestalker.androidkinopoisk.model.reviews.Review;
import ru.nitestalker.androidkinopoisk.viewmodel.ReviewsViewModel;

public class ReviewsActivity extends AppCompatActivity {

    private ReviewsViewModel viewModel;
    private RecyclerView recyclerViewReviews;
    private ReviewsAdapter reviewsAdapter;
    private ProgressBar progressBar;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        movieId = movie.getId();
        initViews();
        initAdapters();
        viewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);
        viewModel.loadReviews(movie.getId()); // Загружаем отзывы к фильму
        viewModel.getListReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewsAdapter.setReviews(reviews);
            }
        });
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (loading)
                    progressBar.setVisibility(View.VISIBLE);
                else progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void initViews() {
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        progressBar = findViewById(R.id.progressBarLoadingReviews);
    }

    public void initAdapters() {
        reviewsAdapter = new ReviewsAdapter();
        recyclerViewReviews.setAdapter(reviewsAdapter);
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        reviewsAdapter.setOnReachEndListener(new ReviewsAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                viewModel.loadReviews(movieId);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}