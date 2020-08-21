package com.example.feedme;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

//abstract class as it is never "really" used
//it can never be instantiated but only extended
    public abstract class BaseActivity extends AppCompatActivity {

        public ProgressBar mProgressBar;

    @Override
    public void setContentView(int layoutResID) {
        //need instance of constraintlayout as it is parent of the layout in our activity_base layout - then inflating it with that content
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        //get our framelayout from the constraintlayout
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        //same for progressbar
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar);
        //will associate framelayout as container for any activities which extend this class
        getLayoutInflater().inflate(layoutResID,frameLayout,true);
        super.setContentView(constraintLayout);
    }
    public void showProgressBar(boolean visibility){
        mProgressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);

    }
}
