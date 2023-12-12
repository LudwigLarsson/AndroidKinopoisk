package ru.nitestalker.androidkinopoisk.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import ru.nitestalker.androidkinopoisk.R;
import ru.nitestalker.androidkinopoisk.model.docs.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private final String TAG = "MoviesAdapter";

    private List<Movie> movieList = new ArrayList<>();
    @Setter
    private OnReachEndListener onReachEndListener;
    @Setter
    private OnMovieClickListener onMovieClickListener;

    @SuppressLint("NotifyDataSetChanged")
    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged(); // НЕ ЗАБЫВАТЬ
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Создаём View из макета и возвращаем ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) { // Вызывается для каждого элемента списка, который нужно отобразить
        Log.d(TAG, "onBindViewHolder: position = " + position);
        Movie movie = movieList.get(position);
        // Устанавливаем постер
        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(holder.imageViewPoster);
        // Устанавливаем рейтинг
        Double rating = movie.getRating().getKp();
        holder.textViewRating.setText(String.valueOf(rating));
        // и соответсвующий фон
        int background = 0;
        if (rating >= 7) background = R.drawable.circle_green;
        else if (rating >= 5) background = R.drawable.circle_yellow;
        else background = R.drawable.circle_red;
        Drawable bg = ContextCompat.getDrawable(holder.itemView.getContext(), background);
        holder.textViewRating.setBackground(bg);

        // Если "рисующийся" элемент находится почти в конце, то вызываем дальнейшую прогрузку элементов
        if (position >= movieList.size() - 10 && onReachEndListener != null)
            onReachEndListener.onReachEnd(); // Логика в MainActivity.onCreate() анонимно

        holder.imageViewPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMovieClickListener != null)
                    onMovieClickListener.onMovieClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MoviesViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewPoster;
        private final TextView textViewRating;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }
    }

    public interface OnReachEndListener { // Callback достижения конца списка на экране
        void onReachEnd();
    }

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

}
