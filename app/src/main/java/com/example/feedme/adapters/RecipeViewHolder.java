package com.example.feedme.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedme.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title, id;
    AppCompatImageView imageView;
    OnRecipeListener onRecipeListener;

    public RecipeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);
        this.onRecipeListener = onRecipeListener;
        title = itemView.findViewById(R.id.recipe_title);
        id = itemView.findViewById(R.id.recipe_id);
        imageView = itemView.findViewById(R.id.recipe_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onRecipeListener.onRecipeClick(getAdapterPosition());

    }
}
