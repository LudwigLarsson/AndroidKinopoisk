package ru.nitestalker.androidkinopoisk.model.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import ru.nitestalker.androidkinopoisk.model.json.Trailer;

@Data
public class TrailersList {

    @SerializedName("trailers")
    private List<Trailer> trailers;

    public TrailersList(List<Trailer> trailers) {
        this.trailers = trailers;
    }

}
