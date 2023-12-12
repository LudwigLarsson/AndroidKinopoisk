package ru.nitestalker.androidkinopoisk.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.nitestalker.androidkinopoisk.R;
import ru.nitestalker.androidkinopoisk.adapter.TrailersAdapter;
import ru.nitestalker.androidkinopoisk.model.docs.Movie;
import ru.nitestalker.androidkinopoisk.model.json.Trailer;
import ru.nitestalker.androidkinopoisk.viewmodel.MovieDetailsViewModel;

public class MovieDetailsActivity extends AppCompatActivity {
    private final String TAG = "MovieDetailsActivity";

    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewRatingStats;
    private TextView textViewReleaseDate;
    private TextView textViewDescription;
    private Button buttonOpenReviews;
    private ImageView imageViewStarAdd2Fav;

    private MovieDetailsViewModel viewModel;
    private TrailersAdapter trailersAdapter;
    private RecyclerView recyclerViewTrailers;
    private Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        initViews();
        initAdapters();
        movie = (Movie) getIntent().getSerializableExtra("movie");
        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewBigPoster);
        textViewTitle.setText(movie.getName());
        textViewReleaseDate.setText(String.valueOf(movie.getYear()));
        textViewDescription.setText(movie.getDescription());
        textViewRatingStats.setText(String.valueOf(movie.getRating().getKp()));
        Drawable starOff = ContextCompat.getDrawable(MovieDetailsActivity.this, R.drawable.favourite_add_to);
        Drawable starOn = ContextCompat.getDrawable(MovieDetailsActivity.this, R.drawable.favourite_remove);
        viewModel.loadTrailers(movie.getId()); // Загружаем трейлеры фильма
        viewModel.getListTrailers().observe(this, new Observer<List<Trailer>>() { // Подписываемся на изменения
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailers(trailers);
            }
        });
        viewModel.getFavouriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDb) {
                if (movieFromDb == null) {
                    imageViewStarAdd2Fav.setImageDrawable(starOff);
                    imageViewStarAdd2Fav.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.insertFavMovie(movie);
                        }
                    });
                } else {
                    imageViewStarAdd2Fav.setImageDrawable(starOn);
                    imageViewStarAdd2Fav.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.removeFavMovie(movie.getId());
                        }
                    });
                }
            }
        });
    }

    private void initAdapters() {
        trailersAdapter = new TrailersAdapter();
        recyclerViewTrailers.setAdapter(trailersAdapter);
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        trailersAdapter.setOnTrailerClickListener(new TrailersAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {
                Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url)); // Переход по url для просмотра трейлера (НЕЯВНЫЙ ИНТЕНТ)
                startActivity(intentToTrailer);
            }
        });
    }

    private void initViews() {
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        imageViewStarAdd2Fav = findViewById(R.id.imageViewAddToFavourite);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewDescription = findViewById(R.id.textViewOverview);
        textViewRatingStats = findViewById(R.id.textViewRatingStats);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        buttonOpenReviews = findViewById(R.id.buttonOpenReviews);
        buttonOpenReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailsActivity.this, ReviewsActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra("movie", movie); // Для передачи кастомных классов они д.б. Serializable
        return intent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}