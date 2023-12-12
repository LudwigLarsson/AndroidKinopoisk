package ru.nitestalker.androidkinopoisk.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import ru.nitestalker.androidkinopoisk.model.reviews.Review;

@Data
public class ReviewResponse {

    @SerializedName("docs")
    private List<Review> reviews;
}
