package com.example.feedme.requests.responses;

import com.example.feedme.models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeSearchResponse {

    @SerializedName("number")
    @Expose()
    private int number;

    @SerializedName("totalResults")
    @Expose()
    private int totalResults;

    @SerializedName("results")
    @Expose()
    private List<Recipe> results;


    public int getNumber() {
        return number;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Recipe> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "number=" + number +
                ", totalResults=" + totalResults +
                ", results=" + results +
                '}';
    }
}
