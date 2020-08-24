package com.example.feedme.models;

import android.os.Parcel;
import android.os.Parcelable;

//parcelable is a way to package custom java classes so they can be put into bundled and given to activities
public class Recipe implements Parcelable {
    private int id;
    private String title;
    private int readyInMinutes;
    private int servings;
    private String sourceUrl;
    private String image;
    private String summary;
    private String instructions;

    public Recipe(int id, String title, int readyInMinutes, int servings, String sourceUrl, String image, String summary, String instructions) {
        this.id = id;
        this.title = title;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.sourceUrl = sourceUrl;
        this.image = image;
        this.summary = summary;
        this.instructions = instructions;
    }

    public Recipe(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        title = in.readString();
        readyInMinutes = in.readInt();
        servings = in.readInt();
        sourceUrl = in.readString();
        image = in.readString();
        summary = in.readString();
        instructions = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getId() {
        return id;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public int getServings() {
        return servings;
    }

    public String getSummary() {
        return summary;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", id=" + id +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", servings=" + servings +
                ", summary='" + summary + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(readyInMinutes);
        dest.writeInt(id);
        dest.writeString(sourceUrl);
        dest.writeInt(servings);
        dest.writeString(summary);
        dest.writeString(instructions);
    }
}
