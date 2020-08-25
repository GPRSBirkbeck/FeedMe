package com.example.feedme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.feedme.models.Recipe;
import com.example.feedme.requests.RecipeApi;
import com.example.feedme.requests.ServiceGenerator;
import com.example.feedme.requests.responses.RecipeResponse;
import com.example.feedme.requests.responses.RecipeSearchResponse;
import com.example.feedme.util.Constants;
import com.example.feedme.viewmodels.RecipeListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {
    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel mRecipeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);

        subScribeObservers();
    }

    public void subScribeObservers(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {

            }
        });
    }

    private void testRetrofitRequest(){
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();

        Call<RecipeSearchResponse> recipeSearchResponseCall = recipeApi
                .searchRecipe("pasta",
                        50,
                        25,
                        Constants.API_KEY);
        recipeSearchResponseCall.enqueue(new Callback<RecipeSearchResponse>(

        ) {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                Log.d(TAG, "onResponse: server response: "+ response.toString());
                if(response.code() ==200){
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    List<Recipe> recipes = new ArrayList<>(response.body().getResults());
                    for(Recipe recipe: recipes){
                        Log.d(TAG, "onResponse: "+ recipe.getTitle());
                    }
                }
                else{
                    try{
                        Log.d(TAG, "onResponse: "+ response.errorBody().string());

                    }catch(IOException e){
                        e.printStackTrace();

                    }
                }

            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {

            }
        });
    }

    private void testRetrofitGetRequest(){
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();

        Call<RecipeResponse> recipeSearchResponseCall = recipeApi
                .getRecipe(1,
                        Constants.API_KEY);
        recipeSearchResponseCall.enqueue(new Callback<RecipeResponse>(

        ) {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                Log.d(TAG, "onResponse: server response: "+ response.toString());
                if(response.code() ==200){
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    Recipe recipe = response.body().getRecipe();
                        Log.d(TAG, "onResponse: RETRIEVED RECIPE"+ recipe.toString());

                }
                else{
                    try{
                        Log.d(TAG, "onResponse: "+ response.errorBody().string());

                    }catch(IOException e){
                        e.printStackTrace();

                    }
                }

            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {

            }
        });
    }
}