package ru.nitestalker.androidkinopoisk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import ru.nitestalker.androidkinopoisk.R;
import ru.nitestalker.androidkinopoisk.model.json.Trailer;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    private List<Trailer> trailers = new ArrayList<>();
    @Setter
    private OnTrailerClickListener onTrailerClickListener;

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.textViewNameOfTrailer.setText(trailer.getName());
        holder.imageViewTrailerPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTrailerClickListener != null)
                    onTrailerClickListener.onTrailerClick(trailer.getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class TrailersViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNameOfTrailer;
        ImageView imageViewTrailerPlay;

        public TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameOfTrailer = itemView.findViewById(R.id.textViewCardTrailerNameOfVideo);
            imageViewTrailerPlay = itemView.findViewById(R.id.imageViewCardTrailerPlayIcon);
        }
    }

    public interface OnTrailerClickListener {
        void onTrailerClick(String url);
    }

}
