package ru.nitestalker.androidkinopoisk.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import ru.nitestalker.androidkinopoisk.model.json.TrailersList;

@Data
public class TrailersResponse {

    @SerializedName("videos")
    private TrailersList trailersList;

    public TrailersResponse(TrailersList trailersList) {
        this.trailersList = trailersList;
    }
}
