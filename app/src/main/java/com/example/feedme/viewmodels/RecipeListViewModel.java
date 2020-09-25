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
    private boolean mIsViewingRecipes;

    public RecipeListViewModel(){
        mIsViewingRecipes = false;
        mRecipeRepository = RecipeRepository.getInstance();
    }

    //the equivalent of using getters and setters, livedata cant be editted, mutablelivedata can
    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getRecipes();
    }

    //method below takes inputs for our repository search method
    public void searchRecipesApi(String query, int maxFat, int number){
        mIsViewingRecipes = true;
        mRecipeRepository.searchRecipesApi(query, maxFat, number);
    }

    public boolean isViewingRecipes() {
        return mIsViewingRecipes;
    }

    public void setIsViewingRecipes(boolean isViewingRecipes) {
        mIsViewingRecipes = isViewingRecipes;
    }
}
