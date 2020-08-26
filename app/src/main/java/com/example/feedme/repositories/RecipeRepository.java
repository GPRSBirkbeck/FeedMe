package com.example.feedme.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.feedme.models.Recipe;
import com.example.feedme.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {
    private static RecipeRepository instance;
    private RecipeApiClient mRecipeApiClient;


    public static RecipeRepository getInstance(){
        if(instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }
    private RecipeRepository(){
        mRecipeApiClient = RecipeApiClient.getInstance();

    }
    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeApiClient.getRecipes();
    }

    //method below takes inputs for our client search query
    public void searchRecipesApi(String query, int maxFat, int number){
        if(number <=0){
            number = 1;
        }
        if(maxFat<0){
            maxFat = 0;
        }
        mRecipeApiClient.searchRecipesApi(query, maxFat, number);
    }
}
