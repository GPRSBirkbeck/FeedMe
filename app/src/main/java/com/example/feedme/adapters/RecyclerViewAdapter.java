package com.example.feedme.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.feedme.R;
import com.example.feedme.models.Recipe;
import com.example.feedme.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;

    private List<Recipe> mRecipes;
    private OnRecipeListener onRecipeListener;

    public RecyclerViewAdapter(OnRecipeListener onRecipeListener) {
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            //the switch determines which type of layout we will use to inflate
            //if the number of viewType is 1; we will build a recipeViewholder
            case RECIPE_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, onRecipeListener);
            }
            //if the number of viewType is 2; we will inflate using a loadingviewholder
            case LOADING_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            }
            case CATEGORY_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list_item, parent, false);
                return new CategoryViewHolder(view, onRecipeListener);
            }
            default:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, onRecipeListener);
            }
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType == RECIPE_TYPE){
            RequestOptions requestOptions = new RequestOptions()
                    //TODO replace the placeholder image with a feedme logo.
                    .placeholder(R.drawable.ic_launcher_background);
            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mRecipes.get(position).getImage())
                    .into(((RecipeViewHolder)holder).imageView);

            //TODO study this and figure out how to bring this across to the ratesAPP
            ((RecipeViewHolder)holder).title.setText(mRecipes.get(position).getTitle());
            ((RecipeViewHolder)holder).id.setText(Integer.toString(mRecipes.get(position).getId()));
        }
        else if(itemViewType == CATEGORY_TYPE){
            RequestOptions requestOptions = new RequestOptions()
                    //TODO replace the placeholder image with a feedme logo.
                    .placeholder(R.drawable.ic_launcher_background);

            Uri path = Uri.parse("android.resource://com.example.feedme/drawable/" + mRecipes.get(position).getImage());
            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(path)
                    .into(((CategoryViewHolder)holder).circleImageView);

            //TODO study this and figure out how to bring this across to the ratesAPP
            ((CategoryViewHolder)holder).title.setText(mRecipes.get(position).getTitle());
        }
    }
    @Override
    public int getItemViewType(int position){
        if(mRecipes.get(position).getId()==-1){
            return CATEGORY_TYPE;
        }
        else if(mRecipes.get(position).getTitle().equals("LOADING...")){
            return  LOADING_TYPE;
        }
        else{
            return RECIPE_TYPE;
        }
    }
    //if i want my RecyclerView to go into loading mode i just
    //need to call displayloading and it will build the list needed to make
    //the loading magic work
    public void displayLoading(){
        if(!isLoading()){
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            mRecipes = loadingList;
            notifyDataSetChanged();
        }

    }
    private boolean isLoading(){
        if(mRecipes!=null){
            if(mRecipes.size()>0){
                if(mRecipes.get(mRecipes.size()-1).getTitle().equals("LOADING...")){
                    return true;
                }
            }
        }
        return false;
    }

    public void displaySearchCategories(){
        List<Recipe> categories = new ArrayList<>();
        for(int i = 0; i< Constants.DEFAULT_SEARCH_CATEGORIES.length; i++){
            Recipe recipe = new Recipe();
            recipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            recipe.setImage(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            //the below is our marker that we are working with categories, not results
            recipe.setId(-1);
            categories.add(recipe);
        }
        mRecipes = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mRecipes != null){
            return mRecipes.size();
        }
        return 0;
    }

    //TODO add one of these for ratesApp
    public void setRecipes(List<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }

}
