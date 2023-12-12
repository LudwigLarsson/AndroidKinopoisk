package ru.nitestalker.androidkinopoisk.model.reviews;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Review {

    @SerializedName("author")
    String author;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("date")
    String date;
    @SerializedName("title")
    String title;
    @SerializedName("type")
    String type = "";
    @SerializedName("review")
    String review;
    @SerializedName("reviewLikes")
    int reviewLikes;
    @SerializedName("reviewDislikes")
    int reviewDislikes;
}
