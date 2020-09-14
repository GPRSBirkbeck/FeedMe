package com.example.feedme.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.feedme.R;
import com.example.feedme.models.Recipe;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Recipe> mRecipes;
    private OnRecipeListener onRecipeListener;

    public RecyclerViewAdapter(OnRecipeListener onRecipeListener) {
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
        return new RecipeViewHolder(view, onRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
