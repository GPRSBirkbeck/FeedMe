package com.example.feedme.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.feedme.AppExecutors;
import com.example.feedme.models.Recipe;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.example.feedme.util.Constants.NETWORK_TIMEOUT;

public class RecipeApiClient {
    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;

    public static RecipeApiClient getInstance(){
        if(instance == null){
            instance = new RecipeApiClient();
        }
        return instance;
    }
    private RecipeApiClient(){
        mRecipes = new MutableLiveData<>();

    }
    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }

    public void searchRecipesApi(){
        //we use the future to set the timeout for the request, and it is getting the scheduled threadpool we built in the AppExecutors class
        final Future handler = AppExecutors.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                //request to the server - data from API please

            }
        });
        //here is our timeout, referencing the constant 3000 miliseconds
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //this will run after those 3 second have passed
                //it will interrupt background thread from running the request to the API
                handler.cancel(true);
                //TODO let user know network has timed out

            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }
}

