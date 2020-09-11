package com.example.feedme.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.feedme.AppExecutors;
import com.example.feedme.models.Recipe;
import com.example.feedme.requests.responses.RecipeSearchResponse;
import com.example.feedme.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.feedme.util.Constants.NETWORK_TIMEOUT;

public class RecipeApiClient {
    private static final String TAG = "RecipeApiClient";
    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;
    private RetrieveRecipesRunnable mRetrieveRecipesRunnable;

    public static RecipeApiClient getInstance(){
        if(instance == null){
            instance = new RecipeApiClient();
        }
        return instance;
    }
    private RecipeApiClient(){ mRecipes = new MutableLiveData<>(); }
    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }
    public void searchRecipesApi(String query, int maxFat, int number){
        if(mRetrieveRecipesRunnable != null){
            mRetrieveRecipesRunnable = null;
        }
        mRetrieveRecipesRunnable = new RetrieveRecipesRunnable(query, maxFat, number);
        //we use the future to set the timeout for the request, and it is getting the scheduled threadpool we built in the AppExecutors class
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveRecipesRunnable);
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
    // runnable class used to retrieve the data from the restAPI
    private class RetrieveRecipesRunnable implements Runnable{
        private String query;
        private int maxFat;
        private int number;
        boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int maxFat, int number) {
            this.query = query;
            this.maxFat = maxFat;
            this.number = number;
            cancelRequest = false;
        }

        //this run is responsible for running the query
        @Override
            public void run() {
            //THIS is the actual line of code that will run on the background thread
            try {
                Response response = getRecipes(query,maxFat,number).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() ==200){
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse)response.body()).getResults());
                    //may need to improve this line of code
                    //if we dont have any in our list yet (sub 15 as 15 is the amount we load per time)
                    if(number<150){
                        //here we are sending value to the livedata
                        //postvalue used for background thread
                        mRecipes.postValue(list);
                    }
                    else{
                        List<Recipe> currentList = mRecipes.getValue();
                        currentList.addAll(list);
                        mRecipes.postValue(currentList);
                    }
                }
                else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRecipes.postValue(null);
            }

        }
        private Call<RecipeSearchResponse> getRecipes(String query, int maxFat, int number){
            return ServiceGenerator.getRecipeApi().searchRecipe(
                    query,
                    maxFat,
                    number,
                    Constants.API_KEY
            );
        }
        private void cancelRequest(){
            Log.d(TAG, "instance initializer: cancelling the search request");
            cancelRequest = true;
        }
    }
}

