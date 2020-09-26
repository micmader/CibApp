package com.example.cibapp;

// https://cutt.ly/cibapp

import android.content.res.TypedArray;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class NormalActivity2 extends AppCompatActivity {

    //Member variables
    private RecyclerView mRecyclerView;
    private ArrayList<Recipe> mRecipesData;
    private RecipesAdapter mAdapter;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("NA2","1");
        setContentView(R.layout.activity_normal2);
        Log.d("NA2","2");
        //Initialize the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Log.d("NA2","3");
        //Set the Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("NA2","4");
        //Initialize the ArrayLIst that will contain the data
        mRecipesData = new ArrayList<>();



        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = new RecipesAdapter(this, mRecipesData);
        mRecyclerView.setAdapter(mAdapter);
        helper.attachToRecyclerView(mRecyclerView);
        //Get the data
        initializeData();

    }

    /**
     * Method for initializing the sports data from resources.
     */
    private void initializeData() {
        //Get the resources from the XML file
        String[] recipesList = getResources().getStringArray(R.array.recipes_titles);
        String[] recipesInfo = getResources().getStringArray(R.array.recipes_info);
        TypedArray recipesImageResources =
                getResources().obtainTypedArray(R.array.recipes_images);

        //Clear the existing data (to avoid duplication)
        mRecipesData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for (int i = 0; i < recipesInfo .length; i++) {
            mRecipesData.add(new Recipe(recipesList[i], recipesInfo[i],
                    recipesImageResources.getResourceId(i, 0)));
        }
        recipesImageResources.recycle();
        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();
    }


    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
            ItemTouchHelper.DOWN | ItemTouchHelper.UP, 0) {
        @Override
        public boolean onMove(RecyclerView recyclerView,
                              RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();
            Collections.swap(mRecipesData, from, to);
            mAdapter.notifyItemMoved(from, to);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder,
                             int direction) {
            mRecipesData.remove(viewHolder.getAdapterPosition());
            mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    });

}