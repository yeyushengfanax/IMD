package com.example.myapplication;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Member variables.
    private RecyclerView mRecyclerView;
    private AnimeViewModel mAnimeViewModel;
    public static final int NEW_ANIME_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_ANIME_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and set it to the RecyclerView.
        final AnimeAdapter mAdapter = new AnimeAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAnimeViewModel = ViewModelProviders.of(this).get(AnimeViewModel.class);

        mAnimeViewModel.getAllAnime().observe(this, new Observer<List<Anime>>() {
            @Override
            public void onChanged(@Nullable final List<Anime> animes) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setAnimes(animes);
            }
        });

        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The SportsViewHolder that is being moved
             * @param target The SportsViewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                //do nothing
                return true;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                int position = viewHolder.getAdapterPosition();
                Anime myAnime = mAdapter.getAnimeAtPosition(position);
                Toast.makeText(MainActivity.this, "Deleting " +
                        myAnime.getTitle(), Toast.LENGTH_LONG).show();

                // Delete the word
                mAnimeViewModel.deleteAnime(myAnime);
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);
        mAdapter.setOnItemClickListener(new AnimeAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Anime anime = mAdapter.getAnimeAtPosition(position);
                mAnimeViewModel.deleteAnime(anime);
                launchUpdateAnimeActivity(anime);
            }
        });
    }

    public void launchUpdateAnimeActivity(Anime anime) {
        Intent intent = new Intent(this, NewAnimeActivity.class);
        startActivityForResult(intent, UPDATE_ANIME_ACTIVITY_REQUEST_CODE);
    }

    public void addNewAnime(View view) {
        Intent intent = new Intent(MainActivity.this, NewAnimeActivity.class);
        startActivityForResult(intent, NEW_ANIME_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ANIME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Anime anime = new Anime(data.getStringExtra("name"),data.getStringExtra("info"),R.drawable.image_weather);
            mAnimeViewModel.insert(anime);
        } else if (requestCode == UPDATE_ANIME_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            Anime anime = new Anime(data.getStringExtra("name"),data.getStringExtra("info"),R.drawable.img_name);
            mAnimeViewModel.insert(anime);
        }else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}