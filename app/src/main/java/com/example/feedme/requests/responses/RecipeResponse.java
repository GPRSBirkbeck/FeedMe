package com.example.feedme.requests.responses;

import com.example.feedme.models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeResponse {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("readyInMinutes")
    @Expose
    private int readyInMinutes;

    @SerializedName("servings")
    @Expose
    private int servings;

    @SerializedName("sourceUrl")
    @Expose
    private String sourceUrl;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("instructions")
    @Expose
    private String instructions;

    Recipe recipe = new Recipe(id,title,readyInMinutes,servings,sourceUrl,image,summary,instructions);

    public Recipe getRecipe(){
        return recipe;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", servings=" + servings +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", image='" + image + '\'' +
                ", summary='" + summary + '\'' +
                ", instructions='" + instructions + '\'' +
                ", recipe=" + recipe +
                '}';
    }
}
