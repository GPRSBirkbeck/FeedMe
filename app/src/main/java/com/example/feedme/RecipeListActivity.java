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
import android.widget.SearchView;

import com.example.feedme.adapters.OnRecipeListener;
import com.example.feedme.adapters.RecyclerViewAdapter;
import com.example.feedme.models.Recipe;
import com.example.feedme.requests.RecipeApi;
import com.example.feedme.requests.ServiceGenerator;
import com.example.feedme.requests.responses.RecipeResponse;
import com.example.feedme.requests.responses.RecipeSearchResponse;
import com.example.feedme.util.Constants;
import com.example.feedme.util.Testing;
import com.example.feedme.util.VerticalSpacingItemDecorator;
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
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView = findViewById(R.id.recipe_list);
        mSearchView = findViewById(R.id.search_view);

        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);

        initRecylcerView();
        subScribeObservers();
        initSearchView();
        if(!mRecipeListViewModel.isViewingRecipes()){
            //if it isnt viewing recipes, display search categories;
            displaySearchCategories();
        }
    }

    public void subScribeObservers(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if(recipes!= null){
                    if(mRecipeListViewModel.isViewingRecipes()){
                        Testing.printRecipes(recipes, "recipes test");
                        mRecipeListViewModel.setIsPerformingQuery(false);
                        mRecyclerViewAdapter.setRecipes(recipes);
                    }
                }
            }
        });
    }

    private void initRecylcerView(){
        mRecyclerViewAdapter = new RecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    private void initSearchView(){
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                mRecyclerViewAdapter.displayLoading();
                mRecipeListViewModel.searchRecipesApi(query,25,14);
                //TODO see how you can apply the clearFocus, as used below, to RatesApp,
                // as this is super useful
                mSearchView.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {
        mRecyclerViewAdapter.displayLoading();
        mRecipeListViewModel.searchRecipesApi(category,25,14);
        mSearchView.clearFocus();
    }

    private void displaySearchCategories(){
        mRecipeListViewModel.setIsViewingRecipes(false);
        mRecyclerViewAdapter.displaySearchCategories();
    }

    @Override
    public void onBackPressed() {
        //method is called every time the back button is called in this activity
        if(mRecipeListViewModel.isBackPressed()){
            //if this is true is means they are already looking at the category, so the default line below
            //will lead them to exit the app
            super.onBackPressed();
        }
        else{
            displaySearchCategories();
        }
    }
}