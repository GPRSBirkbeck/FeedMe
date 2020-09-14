package com.example.feedme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.feedme.adapters.OnRecipeListener;
import com.example.feedme.adapters.RecyclerViewAdapter;
import com.example.feedme.models.Recipe;
import com.example.feedme.requests.RecipeApi;
import com.example.feedme.requests.ServiceGenerator;
import com.example.feedme.requests.responses.RecipeResponse;
import com.example.feedme.requests.responses.RecipeSearchResponse;
import com.example.feedme.util.Constants;
import com.example.feedme.util.Testing;
import com.example.feedme.viewmodels.RecipeListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {
    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel mRecipeListViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView = findViewById(R.id.recipe_list);

        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);

        initRecylcerView();
        subScribeObservers();
        testRetrofitGetRequest();
    }

    public void subScribeObservers(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if(recipes!= null){
                    Testing.printRecipes(recipes, "recipes test");
                    mRecyclerViewAdapter.setRecipes(recipes);
                }
            }
        });
    }

    private void initRecylcerView(){
        mRecyclerViewAdapter = new RecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    //method below takes inputs for our repository search method
    public void searchRecipesApi(String query, int maxFat, int number){
        mRecipeListViewModel.searchRecipesApi(query, maxFat, number);
    }

    private void testRetrofitGetRequest(){
        searchRecipesApi("pasta",25,14);
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}