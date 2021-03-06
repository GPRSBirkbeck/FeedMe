package com.example.feedme.requests;

import com.example.feedme.requests.responses.RecipeResponse;
import com.example.feedme.requests.responses.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeApi {
    //SEARCH
    //bsically below you want to construct what comes after
    //https://api.spoonacular.com/recipes/complexSearch
    //query is the ingredient or food type you're looking for
    //maxFat, as an example, is the maximum fat content
    //number is the number of recipes per response
    // SEARCH
    //this takes the URL in our constants file and uses that as the Base url
    //using @query appends things to the end of our search url
    @GET("recipes/complexSearch")
    Call<RecipeSearchResponse> searchRecipe(
        @Query("query") String query, // first appends a ?
        //can replace maxfat with something more relevant
        @Query("maxFat") int maxFat, // second and beyond append an & sign
        @Query("number") int number,
        @Query("apiKey") String apiKey
    );

    //GET RECIPE REQUEST
    //Below the @Path is used to provide the parameter for the recipenumber needed in the middle of the response query,
    //Whilst @Query is used for the ?X after the api
    @GET("recipes/{recipeNumber}/information")
    Call<RecipeResponse> getRecipe(
            @Path("recipeNumber") int recipeNumber,
            @Query("apiKey") String apiKey
    );

}
