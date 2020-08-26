package com.example.feedme.viewmodels;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.feedme.models.Recipe;
import com.example.feedme.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private RecipeRepository mRecipeRepository;

    public RecipeListViewModel(){
        mRecipeRepository = RecipeRepository.getInstance();
    }

    //the equivalent of using getters and setters, livedata cant be editted, mutablelivedata can
    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getRecipes();
    }

    //method below takes inputs for our repository search method
    public void searchRecipesApi(String query, int maxFat, int number){
        mRecipeRepository.searchRecipesApi(query, maxFat, number);
    }
}