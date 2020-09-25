package com.example.feedme.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedme.R;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    OnRecipeListener listener;
    CircleImageView circleImageView;
    TextView title;

    public CategoryViewHolder(@NonNull View itemView, OnRecipeListener listener) {
        super(itemView);
        this.listener = listener;
        this.circleImageView = itemView.findViewById(R.id.category_image);
        this.title = itemView.findViewById(R.id.category_title);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onCategoryClick(title.getText().toString());

    }
}
