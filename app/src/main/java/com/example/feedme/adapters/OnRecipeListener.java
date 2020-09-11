package com.example.feedme.adapters;

public interface OnRecipeListener {
    //detect clicks to a recipe
    void onRecipeClick(int position);
    void onCategoryClick(String category);

}
