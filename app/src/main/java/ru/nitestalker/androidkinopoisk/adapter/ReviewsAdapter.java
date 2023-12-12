package ru.nitestalker.androidkinopoisk.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lombok.Setter;
import ru.nitestalker.androidkinopoisk.R;
import ru.nitestalker.androidkinopoisk.model.reviews.Review;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private final String TAG = "ReviewsAdapter";

    private final String TYPE_POSITIVE = "Позитивный";
    private final String TYPE_NEGATIVE = "Негативный";


    private List<Review> reviews = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Setter
    private OnReachEndListener onReachEndListener;

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        Log.d(TAG, "position = " + position);
        Review review = reviews.get(position);
        holder.textViewReviewAuthorName.setText(review.getAuthor());
        holder.textViewDislikesCount.setText(String.valueOf(review.getReviewLikes()));
        holder.textViewLikesCount.setText(String.valueOf(review.getReviewLikes()));
        holder.textViewReviewTitle.setText(review.getTitle());
        holder.textViewCardReviewText.setText(review.getReview());
        DateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.forLanguageTag("ru"));
        try {
            Date date = ISO8601Utils.parse(review.getDate(), new ParsePosition(0));
            holder.textViewReviewDate.setText(df.format(date));
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
        switch (review.getType()) {
            case TYPE_POSITIVE:
//                ContextCompat.getColor(holder.itemView.getContext(), R.color.grey);
                holder.constraintLayoutReview.setBackgroundResource(R.color.green);
                break;
            case TYPE_NEGATIVE:
                holder.constraintLayoutReview.setBackgroundResource(R.color.red);
                break;
            default:
                holder.constraintLayoutReview.setBackgroundResource(R.color.grey);
                break;
        }
        if (position >= reviews.size() - 3 && onReachEndListener != null)
            onReachEndListener.onReachEnd(); // Логика в MovieDetailsActivity.onCreate() анонимно
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        private final View constraintLayoutReview;
        private final TextView textViewCardReviewText;
        private final TextView textViewReviewAuthorName;
        private final TextView textViewReviewDate;
        private final TextView textViewReviewTitle;
        private final TextView textViewLikesCount;
        private final TextView textViewDislikesCount;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayoutReview = itemView.findViewById(R.id.constraintLayoutReview);
            textViewCardReviewText = itemView.findViewById(R.id.textViewCardReviewText);
            textViewReviewAuthorName = itemView.findViewById(R.id.textViewReviewAuthorName);
            textViewReviewDate = itemView.findViewById(R.id.textViewReviewDate);
            textViewReviewTitle = itemView.findViewById(R.id.textViewReviewTitle);
            textViewLikesCount = itemView.findViewById(R.id.textViewLikesCount);
            textViewDislikesCount = itemView.findViewById(R.id.textViewDislikesCount);
        }
    }

    public interface OnReachEndListener { // Callback достижения конца списка на экране
        void onReachEnd();
    }
}
